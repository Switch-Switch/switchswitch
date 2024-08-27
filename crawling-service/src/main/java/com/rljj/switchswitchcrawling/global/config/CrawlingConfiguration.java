package com.rljj.switchswitchcrawling.global.config;

import com.rljj.switchswitchcrawling.domain.crawling.CrawlingNintendoStore;
import com.rljj.switchswitchcrawling.domain.crawling.CrawlingRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrawlingConfiguration {

    @Bean
    public CrawlingRunner crawlingRunner() {
        return new CrawlingNintendoStore();
    }
}
