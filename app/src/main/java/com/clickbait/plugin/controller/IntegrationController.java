package com.clickbait.plugin.controller;

import com.clickbait.plugin.gateways.RssRequestGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration")
public class IntegrationController {
    @Autowired
    private RssRequestGateway gateway;

    @GetMapping(path = "/print/{text}")
    public String rssPrint(@PathVariable("text") String text) {
        return gateway.rssPrint(text);
    }

    @GetMapping(path = "/store/{text}")
    public String rssStore(@PathVariable("text") String text) {
        return gateway.rssStore(text);
    }
}
