package com.alrussy_dev.notification_service.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JavaEmailSenderConfig {

    @Bean
    JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("localhost");
        javaMailSender.setPort(1025);
        javaMailSender.setUsername("PLACEHOLDER");
        javaMailSender.setPassword("PLACEHOLDER");

        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", JavaMailSenderImpl.DEFAULT_PROTOCOL);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.startles.enable", "true");

        //	    properties.setProperty("mail.smtp.connectiontimeout","10000");
        //	    properties.setProperty("mail.smtp.timeout","10000");
        //	    properties.setProperty("mail.smtp.writetimeout","10000");

        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
}
