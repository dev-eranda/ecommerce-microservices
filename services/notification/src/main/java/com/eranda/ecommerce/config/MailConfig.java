package com.eranda.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost"); // MailDev host
        mailSender.setPort(1025); // MailDev port

        // Optional: Set additional properties
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "false"); // No authentication for MailDev
        props.put("mail.smtp.starttls.enable", "false"); // No TLS for MailDev
        props.put("mail.debug", "true"); // Debug mode for troubleshooting

        return mailSender;
    }
}
