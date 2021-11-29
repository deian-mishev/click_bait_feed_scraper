package com.clickbait.plugin;

import com.clickbait.plugin.gateways.RssRequestGateway;
import com.rometools.rome.feed.synd.SyndEntryImpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PluginFeedScraper {

    public static void main(String... args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(PluginFeedScraper.class, args);

        SyndEntryImpl syndEntry = new SyndEntryImpl();
        RssRequestGateway a = ctx.getBean(RssRequestGateway.class);

        for (int i = 0; i < 10; i++) {
            String m = i + "";
            syndEntry.setTitle(m);
            syndEntry.setAuthor(m);
            syndEntry.setLink(m);
            a.rssRequest(syndEntry);
        }

        a.rssStore("SAVED");
        a.rssPrint("PRINTED");

        System.out.println("Hit 'Enter' to terminate");
        System.in.read();
        ctx.close();
    }
}
