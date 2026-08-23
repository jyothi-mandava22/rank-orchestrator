package com.ats.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RankingApplication {
    public static void main(String[] args) {
        SpringApplication.run(RankingApplication.class, args);
    }
}