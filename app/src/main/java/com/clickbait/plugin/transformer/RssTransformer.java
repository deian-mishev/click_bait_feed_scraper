package com.clickbait.plugin.transformer;

import com.clickbait.plugin.config.RssConfig;
import com.rometools.rome.feed.synd.SyndEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.transformer.AbstractPayloadTransformer;

@Configuration
public class RssTransformer {

    @Autowired
    private RssConfig config;

    @Bean(name = "extractLinkFromFeed")
    @Transformer(inputChannel = "transform", outputChannel = "integration.gateway.split")
    public AbstractPayloadTransformer<SyndEntry, String> extractLinkFromFeed() {
        return new AbstractPayloadTransformer<SyndEntry, String>() {
            @Override
            protected String transformPayload(SyndEntry payload) {
                return String.format(config.getTransform(), payload.getTitle(), payload.getAuthor(), payload.getLink())
                        + System.lineSeparator();
            }
        };
    }

    @Bean(name = "addSeparatorToStore")
    @Transformer(inputChannel = "integration.gateway.direct.store", outputChannel = "integration.gateway.direct.store.fixed")
    public AbstractPayloadTransformer<String, String> storePrintFix() {
        return new AbstractPayloadTransformer<String, String>() {
            @Override
            protected String transformPayload(String payload) {
                return payload + System.lineSeparator();
            }
        };
    }
}
