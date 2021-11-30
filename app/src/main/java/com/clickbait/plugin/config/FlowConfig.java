package com.clickbait.plugin.config;

import java.net.URL;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.integration.feed.dsl.FeedEntryMessageSourceSpec;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class FlowConfig {
    @Autowired
    private GenericApplicationContext context;

    @Autowired
    @Qualifier("rssMetadataStore")
    private MetadataStore metadataStore;

    @Autowired
    private RssConfig config;

    @PostConstruct
    public void registerFlows() {
        for (URL el : config.getFeeds()) {
            context.registerBean(el.toString(), IntegrationFlow.class,
                    () -> rssFeed(metadataStore, el, config.getTopic()));
            context.getBean(el.toString());
        }
    }

    public FeedEntryMessageSourceSpec feedMessageSource(MetadataStore metadataStore, URL feed, String topic) {
        return Feed.inboundAdapter(feed, topic).metadataStore(metadataStore);
    }

    public IntegrationFlow rssFeed(MetadataStore metadataStore, URL feed, String topic) {
        return IntegrationFlows
                .from(feedMessageSource(metadataStore, feed, topic),
                        e -> e.poller(c -> c.fixedDelay(5000)))
                .fixedSubscriberChannel()
                .channel("transform")
                .get();
    }
}
