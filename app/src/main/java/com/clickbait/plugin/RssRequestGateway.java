package com.clickbait.plugin;

import com.rometools.rome.feed.synd.SyndEntryImpl;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface RssRequestGateway {
    @Gateway(requestChannel = "directChannel")
    void rssRequest(String syndEntry);
}
