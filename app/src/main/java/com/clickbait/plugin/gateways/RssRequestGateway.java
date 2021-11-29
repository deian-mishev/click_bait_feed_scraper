package com.clickbait.plugin.gateways;

import com.rometools.rome.feed.synd.SyndEntryImpl;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface RssRequestGateway {
    @Gateway(requestChannel = "directChannel")
    void rssRequest(SyndEntryImpl syndEntry);

    @Gateway(requestChannel = "integration.gateway.store")
    String rssStore(String entry);

    @Gateway(requestChannel = "integration.gateway.print")
    String rssPrint(String entry);
}
