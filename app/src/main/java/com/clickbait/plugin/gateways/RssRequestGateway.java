package com.clickbait.plugin.gateways;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface RssRequestGateway {
    @Gateway(requestChannel = "integration.gateway.direct")
    public <T> void rssRequest(T syndEntry);

    @Gateway(requestChannel = "integration.gateway.direct.store", replyChannel = "integration.gateway.replay")
    public <T> T rssStore(T entry);

    @Gateway(requestChannel = "integration.gateway.direct.print", replyChannel = "integration.gateway.replay")
    public <T> T rssPrint(T entry);
}
