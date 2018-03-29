package com.rhino.translateapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google")
@PropertySource("classpath:config/google.api.url.properties")
public class ApiBean {
    private String tts_url;

    public String getTts_url() {
        return tts_url;
    }

    public void setTts_url(String tts_url) {
        this.tts_url = tts_url;
    }
}
