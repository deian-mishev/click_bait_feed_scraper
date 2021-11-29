package com.clickbait.plugin.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.clickbait.plugin.config.RssConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class ServiceActivators {

    @Autowired
    private RssConfig config;

    @Bean // to handler instead of channel
    @ServiceActivator(inputChannel = "integration.gateway.print", outputChannel = "integration.gateway.response")
    public MessageHandler printHandler() {
        return System.out::println;
    }

    @Bean
    @ServiceActivator(inputChannel = "integration.gateway.store", outputChannel = "integration.gateway.response")
    public MessageHandler storeHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(config.getTargetFolder()));
        handler.setFileNameGenerator(
                (Message<?> message) -> new SimpleDateFormat(config.getTargetFormat()).format(new Date()));
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setCharset("UTF-8");
        handler.setExpectReply(false);
        return handler;
    }

    @ServiceActivator(inputChannel = "splitterHandler")
    public RecipientListRouter splitterHandler() {
        RecipientListRouter router = new RecipientListRouter();
        router.addRecipient("integration.gateway.print");
        router.addRecipient("integration.gateway.store");
        return router;
    }

    @Bean
    @ServiceActivator(inputChannel = "integration.gateway.response")
    public void storeResponse(Message<String> message) {
        System.out.println(message);
    }
}
