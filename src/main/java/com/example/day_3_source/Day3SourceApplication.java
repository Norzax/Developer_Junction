package com.example.day_3_source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Day3SourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Day3SourceApplication.class, args);
    }

}
