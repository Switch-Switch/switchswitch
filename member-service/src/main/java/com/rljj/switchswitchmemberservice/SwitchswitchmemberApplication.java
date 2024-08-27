package com.rljj.switchswitchmemberservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SwitchswitchmemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwitchswitchmemberApplication.class, args);
	}
}
