package io.github.bodin;

import io.github.bodin.domain.ApiClient;
import io.github.bodin.domain.LocalClientRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Configuration
    public static class DefaultSecurityConfig {


        @Bean
        SecurityFilterChain chain1(HttpSecurity http) throws Exception {

            OAuth2AuthorizationServerConfigurer asConfig =
                    OAuth2AuthorizationServerConfigurer.authorizationServer();

            return http
                    .securityMatcher(asConfig.getEndpointsMatcher())
                    .with(asConfig, as-> {})
                    .authorizeHttpRequests(a -> a.anyRequest().authenticated())
                    .build();
        }

        @Bean
        SecurityFilterChain chain2(HttpSecurity http) throws Exception {

            return http
                    .securityMatcher("/site/**", "/index.js", "/site.css")
                    .authorizeHttpRequests(a -> a.anyRequest().permitAll())
                    .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        @Bean
        public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(LocalClientRepository repo) {
            return context -> {
                if (context.getTokenType().getValue().equals("access_token")) {
                    ApiClient c = repo.lookup(context.getRegisteredClient().getId());
                    context.getClaims()
                            // used for rate limiting and access
                            .claim("client_subscription", c.getSubscription())
                            // used for rate limiting
                            .claim("client_id", c.getClientId())
                            // used for logging / metrics
                            .claim("client_name", c.getClientName());
                }
            };
        }
    }
}