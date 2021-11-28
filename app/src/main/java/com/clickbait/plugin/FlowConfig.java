package com.clickbait.plugin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rometools.rome.feed.synd.SyndEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.integration.feed.dsl.FeedEntryMessageSourceSpec;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.metadata.PropertiesPersistingMetadataStore;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@Configuration
public class FlowConfig {

    @Autowired
    private RssConfig config;

    // MetadataStore
    @Bean
    public MetadataStore metadataStore() {
        PropertiesPersistingMetadataStore metadataStore = new PropertiesPersistingMetadataStore();
        metadataStore.setBaseDirectory(config.getMetadataFolder());
        return metadataStore;
    }

    // MessageHandlers
    @Bean
    public MessageHandler printHandler() {
        return System.out::println;
    }

    @Bean
    public MessageHandler storeHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(config.getTargetFolder()));
        handler.setFileNameGenerator(
                (Message<?> message) -> new SimpleDateFormat(config.getTargetFormat()).format(new Date()));
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setCharset("UTF-8");
        handler.setExpectReply(false);
        return handler;
    }

    // Transform
    @Bean
    public AbstractPayloadTransformer<SyndEntry, String> extractLinkFromFeed() {
        return new AbstractPayloadTransformer<SyndEntry, String>() {
            @Override
            protected String transformPayload(SyndEntry payload) {
                return String.format(config.getTransform(), payload.getTitle(), payload.getAuthor(), payload.getLink())
                        + System.lineSeparator();
            }
        };
    }

    // Poller
    @Bean(name = "pollerMetadata")
    public PollerMetadata poller() {
        return Pollers.fixedDelay(config.getPoll()).get();
    }

    // Channels
    @Bean(name = "directChannel")
    @BridgeTo("splitChannel")
    public MessageChannel directChannel() {
        return new DirectChannel();
    }

    @Bean(name = "inboundAdapter")
    public FeedEntryMessageSourceSpec inboundAdapter() {
        return Feed.inboundAdapter(config.getFeed(), config.getTopic()).metadataStore(metadataStore());
    }

    @Bean(name = "splitChannel")
    public MessageChannel splitChannel() {
        return MessageChannels.publishSubscribe("splitChannel").get();
    }

    // Flows
    @Bean(name = "rssFeed")
    public IntegrationFlow rssFeed(PollerMetadata pollerMetadata) {
        return IntegrationFlows
                .from(inboundAdapter(), e -> e.poller(pollerMetadata))
                .transform(extractLinkFromFeed())
                .channel(splitChannel())
                .get();
    }

    @Bean
    public IntegrationFlow messageHandlerFlow() {
        return IntegrationFlows
                .from("splitChannel")
                .handle(printHandler())
                .get();
    }

    @Bean
    public IntegrationFlow storeHandlerFlow() {
        return IntegrationFlows
                .from("splitChannel")
                .handle(storeHandler())
                .get();
    }
}
