package com.cgi.trialtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CgiTrialTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(CgiTrialTaskApplication.class, args);
    }

}
