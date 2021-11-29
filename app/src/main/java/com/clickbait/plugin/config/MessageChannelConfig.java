package com.clickbait.plugin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class MessageChannelConfig {

    @Bean(name = "directChannel")
    @BridgeTo("transform")
    public MessageChannel directChannel() {
        return new DirectChannel();
    }

    @Bean(name = "storeAndPrint")
    public MessageChannel storeAndPrint() {
        return MessageChannels.publishSubscribe("storeAndPrint").get();
    }
}
