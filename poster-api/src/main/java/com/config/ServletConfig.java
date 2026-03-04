package com.config;

import com.servlet.PosterServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration pour enregistrer les Servlets dans le conteneur Spring Boot.
 */
@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<PosterServlet> posterServletRegistration() {
        ServletRegistrationBean<PosterServlet> registration = new ServletRegistrationBean<>(new PosterServlet(), "/poster/*");
        registration.setLoadOnStartup(1);
        return registration;
    }
}
