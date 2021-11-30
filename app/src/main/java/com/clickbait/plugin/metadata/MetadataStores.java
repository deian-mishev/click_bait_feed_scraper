package com.clickbait.plugin.metadata;

import com.clickbait.plugin.config.RssConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.metadata.PropertiesPersistingMetadataStore;

@Configuration
public class MetadataStores {
    @Autowired
    private RssConfig config;

    @Bean("rssMetadataStore")
    public MetadataStore metadataStore() {
        PropertiesPersistingMetadataStore metadataStore = new PropertiesPersistingMetadataStore();
        metadataStore.setBaseDirectory(config.getMetadataFolder());
        return metadataStore;
    }
}
