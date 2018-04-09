package com.ErasmusProject.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = { "com.ErasmusProject.app", "com.ErasmusProject.controller", "com.ErasmusProject.util"})
public class ModuleRecommenderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleRecommenderServiceApplication.class, args);
	}
}
