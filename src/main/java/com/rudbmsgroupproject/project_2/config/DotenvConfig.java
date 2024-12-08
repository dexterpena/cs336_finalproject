package com.rudbmsgroupproject.project_2.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    static {
        Dotenv dotenv = Dotenv.load();  // Load the .env file

        // Set environment variables in the system environment
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        // Add more environment variables as needed
    }

    @Bean
    public String dbUrl() {
        return System.getProperty("DB_URL");
    }

    @Bean
    public String dbUsername() {
        return System.getProperty("DB_USERNAME");
    }

    @Bean
    public String dbPassword() {
        return System.getProperty("DB_PASSWORD");
    }
}