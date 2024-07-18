package com.assignment.BankingApp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableMethodSecurity
@Configuration
public class ApiSecurityConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ApiSecurityConfiguration.class);

    @Value("${api.security.ignored}")
    private String[] ignored;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        logger.info("Ignored paths: {}", (Object) ignored); // Log the ignored paths
        return web -> {
            for (String location : ignored) {
                web.ignoring().requestMatchers(new AntPathRequestMatcher(location));
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(config -> config.anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));;
        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        UserDetails adminUser = User.withUsername("admin")
//                .password("{bcrypt}$2a$10$MfV6YnK1jA23FeT6onJbEeXCNJksAJj1alhXZse6GhtHP8D6CU7Bq")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(adminUser);
//    }
}
