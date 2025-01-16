package com.rljj.crawlingservice.global.config.crawling;

import com.rljj.crawlingservice.domain.crawling.CrawlingNintendoStore;
import com.rljj.crawlingservice.domain.crawling.CrawlingRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrawlingConfiguration {

    @Bean
    public CrawlingRunner crawlingRunner() {
        return new CrawlingNintendoStore();
    }
}
