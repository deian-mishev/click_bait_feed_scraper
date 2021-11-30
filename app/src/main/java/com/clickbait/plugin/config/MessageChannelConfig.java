package com.clickbait.plugin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MessageChannelConfig {

    @Bean(name = "integration.gateway.direct")
    @BridgeTo("transform")
    public MessageChannel directChannel() {
        return new DirectChannel();
    }

    @Bean(name = "integration.gateway.split")
    public MessageChannel storeAndPrint() {
        return MessageChannels.publishSubscribe("integration.gateway.split").get();
    }

    @Bean(name = "integration.gateway.replay")
    public MessageChannel replayChannel() {
        return new DirectChannel();
    }
}
