package io.github.bodin.interceptor;

import io.github.bodin.API;
import io.github.bodin.annotation.Subscription;
import io.github.bodin.subscription.SubscriptionLevel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

@Component
public class SuccessInterceptor extends AuthenticatedMethodHandlerInterceptor {

    @Override
    public void postHandleAuthenticated(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, ModelAndView modelAndView) throws Exception {
        if(response.getStatus() < 300){
            metrics.success(API.getClientId(), String.valueOf(response.getStatus()));
        }else{
            metrics.failure(API.getClientId(), String.valueOf(response.getStatus()));
        }
    }
}