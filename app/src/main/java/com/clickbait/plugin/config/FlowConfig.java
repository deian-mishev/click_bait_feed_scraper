package com.clickbait.plugin.config;

import com.clickbait.plugin.transformer.RssTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.integration.feed.dsl.FeedEntryMessageSourceSpec;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.metadata.PropertiesPersistingMetadataStore;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;

@Configuration
public class FlowConfig {

    @Autowired
    private RssConfig config;

    @Autowired
    private RssTransformer transformer;

    @Bean
    public MetadataStore metadataStore() {
        PropertiesPersistingMetadataStore metadataStore = new PropertiesPersistingMetadataStore();
        metadataStore.setBaseDirectory(config.getMetadataFolder());
        return metadataStore;
    }

    @Bean(name = "feedMessageSource")
    public FeedEntryMessageSourceSpec feedMessageSource() {
        return Feed.inboundAdapter(config.getFeed(), config.getTopic()).metadataStore(metadataStore());
    }

    @Bean
    public IntegrationFlow transformFeed() {
        return IntegrationFlows
                .from("transform")
                .transform(transformer.extractLinkFromFeed())
                .channel("integration.gateway.split")
                .get();
    }

    @Bean
    public IntegrationFlow rssFeed(@Qualifier("rssPoller") PollerMetadata pollerMetadata) {
        return IntegrationFlows
                .from(feedMessageSource(), e -> e.poller(pollerMetadata))
                .fixedSubscriberChannel()
                .channel("transform")
                .get();
    }

    @Bean
    public IntegrationFlow fromStoreAndPrint() {
        return IntegrationFlows
                .from("integration.gateway.split")
                .routeToRecipients(r -> r
                        .recipient("integration.gateway.print")
                        .recipient("integration.gateway.store"))
                .get();
    }

    @Bean
    public IntegrationFlow fromApiPrint() {
        return IntegrationFlows
                .from("integration.gateway.direct.print")
                .routeToRecipients(r -> r
                        .recipient("integration.gateway.print.response")
                        .recipient("integration.gateway.print"))
                .get();
    }

    @Bean
    public IntegrationFlow fromApiStore() {
        return IntegrationFlows
                .from("integration.gateway.direct.store")
                .handle(Message.class, (a, b) -> {
                    return a.getPayload() + System.lineSeparator();
                })
                .routeToRecipients(r -> r
                        .recipient("integration.gateway.store.response")
                        .recipient("integration.gateway.store"))
                .get();
    }
}
