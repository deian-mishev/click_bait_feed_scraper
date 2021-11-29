package com.clickbait.plugin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

@Configuration
public class PollersConfig {
    @Autowired
    private RssConfig config;

    @Bean(name = "rssPoller")
    public PollerMetadata poller() {
        return Pollers.fixedDelay(config.getPollRss()).get();
    }
}
