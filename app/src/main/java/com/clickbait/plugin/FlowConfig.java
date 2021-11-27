package com.clickbait.plugin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rometools.rome.feed.synd.SyndEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.integration.feed.dsl.FeedEntryMessageSourceSpec;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.metadata.PropertiesPersistingMetadataStore;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.Message;

@Configuration
public class FlowConfig {

    @Autowired
    private RssConfig config;

    @Bean
    public MetadataStore metadataStore() {
        PropertiesPersistingMetadataStore metadataStore = new PropertiesPersistingMetadataStore();
        metadataStore.setBaseDirectory(config.getMetadataFolder());
        return metadataStore;
    }

    @Bean
    public MessageHandler targetDirectory() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(config.getTargetFolder()));
        handler.setFileNameGenerator(
                (Message<?> message) -> new SimpleDateFormat(config.getTargetFormat()).format(new Date()));
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setCharset("UTF-8");
        handler.setExpectReply(false);
        return handler;
    }

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

    @Bean
    public FeedEntryMessageSourceSpec inboundAdapter() {
        return Feed.inboundAdapter(config.getFeed(), config.getTopic()).metadataStore(metadataStore());
    }

    @Bean
    public IntegrationFlow feedFlow() {
        return IntegrationFlows
                .from(inboundAdapter(),
                        e -> e.poller(p -> p.fixedDelay(config.getPoll())))
                .transform(extractLinkFromFeed()).handle(targetDirectory())
                .get();
    }
}
