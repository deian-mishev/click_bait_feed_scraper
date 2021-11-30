package com.clickbait.plugin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PluginFeedScraper {

    public static void main(String... args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(PluginFeedScraper.class, args);
        System.out.println("Hit 'Enter' to terminate");
        System.in.read();
        ctx.close();
    }
}
