package io.github.bodin;

import io.github.bodin.interceptor.RateLimitInterceptor;
import io.github.bodin.interceptor.ScopeInterceptor;
import io.github.bodin.interceptor.SubscriptionInterceptor;
import io.github.bodin.interceptor.SuccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableMethodSecurity
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Component
    public static class WebConfigure implements WebMvcConfigurer {

        @Autowired
        private RateLimitInterceptor interceptor1;

        @Autowired
        private SubscriptionInterceptor interceptor2;

        @Autowired
        private ScopeInterceptor interceptor3;

        @Autowired
        private SuccessInterceptor interceptor4;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(interceptor1);
            registry.addInterceptor(interceptor2);
            registry.addInterceptor(interceptor3);
            registry.addInterceptor(interceptor4);
        }
    }
}