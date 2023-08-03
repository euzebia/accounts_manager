package com.datamanagerapi.datamanagerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DatamanagerapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatamanagerapiApplication.class, args);
    }

}
