package com.clickbait.plugin.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.clickbait.plugin.config.RssConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class ServiceActivators {

    @Autowired
    private RssConfig config;

    // Handlers
    @Bean
    @ServiceActivator(inputChannel = "integration.gateway.print")
    public MessageHandler printHandler() {
        return System.out::println;
    }

    @Bean
    @ServiceActivator(inputChannel = "integration.gateway.store")
    public MessageHandler storeHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(config.getTargetFolder()));
        handler.setFileNameGenerator(
                (Message<?> message) -> new SimpleDateFormat(config.getTargetFormat()).format(new Date()));
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setCharset("UTF-8");
        handler.setExpectReply(false);
        return handler;
    }

    // True Activators
    @ServiceActivator(inputChannel = "integration.gateway.direct.store", outputChannel = "integration.gateway.direct.store.fixed")
    public Message<String> storePrintFix(Message<String> message) {
        MessageBuilder.fromMessage(message);
        Message<String> replay = MessageBuilder
                .withPayload(message.getPayload() + System.lineSeparator())
                .build();
        return replay;
    }

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
