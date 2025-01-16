package com.rljj.crawlingservice.global.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.rljj.switchswitchentity")
public class JpaConfig {
}
