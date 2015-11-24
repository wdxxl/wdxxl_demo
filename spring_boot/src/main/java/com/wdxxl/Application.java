package com.wdxxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        // For Banner if application.properties not add spring.main.show-banner = true
        /*
         * SpringApplication app = new SpringApplication(MySpringConfiguration.class);
         * app.setShowBanner(true);
         * app.run(args);
         */
    }
}
