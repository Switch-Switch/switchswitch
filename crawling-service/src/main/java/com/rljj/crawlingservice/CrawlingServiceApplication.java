package com.rljj.crawlingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CrawlingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlingServiceApplication.class, args);
	}
}
