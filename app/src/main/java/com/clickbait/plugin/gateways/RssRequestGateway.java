package com.clickbait.plugin.gateways;

import com.rometools.rome.feed.synd.SyndEntryImpl;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface RssRequestGateway {
    @Gateway(requestChannel = "directChannel")
    void rssRequest(SyndEntryImpl syndEntry);

    @Gateway(requestChannel = "store")
    void rssStore(String entry);

    @Gateway(requestChannel = "print")
    void rssPrint(String entry);
}
