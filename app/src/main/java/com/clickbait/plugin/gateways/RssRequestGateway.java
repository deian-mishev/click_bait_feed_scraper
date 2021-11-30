package com.clickbait.plugin.gateways;

import com.rometools.rome.feed.synd.SyndEntryImpl;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface RssRequestGateway {
    @Gateway(requestChannel = "integration.gateway.direct")
    public void rssRequest(SyndEntryImpl syndEntry);

    @Gateway(requestChannel = "integration.gateway.direct.store", replyChannel = "integration.gateway.replay")
    public String rssStore(String entry);

    @Gateway(requestChannel = "integration.gateway.direct.print", replyChannel = "integration.gateway.replay")
    public String rssPrint(String entry);
}
