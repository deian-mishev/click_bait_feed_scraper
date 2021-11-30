package com.clickbait.plugin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import org.springframework.stereotype.Component;

@Component
public class ServiceActivators {

    @Autowired
    @Qualifier("rssMetadataStore")
    private MetadataStore metadataStore;

    @ServiceActivator(inputChannel = "integration.gateway.store.response", outputChannel = "integration.gateway.replay")
    public Message<String> storeReplay(Message<String> message) {
        MessageBuilder.fromMessage(message);
        Message<String> replay = MessageBuilder
                .withPayload("Storing " + message.getPayload())
                .build();
        return replay;
    }

    @ServiceActivator(inputChannel = "integration.gateway.print.response", outputChannel = "integration.gateway.replay")
    public Message<String> printReplay(Message<String> message) {
        MessageBuilder.fromMessage(message);
        Message<String> replay = MessageBuilder
                .withPayload("Printing " + message.getPayload())
                .build();
        return replay;
    }
}
