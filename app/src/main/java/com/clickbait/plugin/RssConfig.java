package com.clickbait.plugin;

import java.net.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties
public class RssConfig {
    private URL feed;
    private String topic;
    private Integer poll;
    private String transform;
    private String targetFolder;
    private String targetFormat;
    private String metadataFolder;
}
