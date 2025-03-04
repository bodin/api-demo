package io.github.bodin.interceptor;

import io.github.bodin.API;
import io.github.bodin.metrics.MetricsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public abstract class AuthenticatedMethodHandlerInterceptor extends MethodHandlerInterceptor {

    @Autowired
    protected MetricsService metrics;

    public boolean preHandleAuthenticated(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {
        return true;
    }
    public void postHandleAuthenticated(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, ModelAndView modelAndView) throws Exception{

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {

        if (API.currentJwt().isEmpty()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("Invalid Request, no token found");
            return false;
        }

        if (API.getClientId() == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("Invalid Request, no client_id found");
            return false;
        }

        return this.preHandleAuthenticated(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, ModelAndView modelAndView) throws Exception {
        Optional<Jwt> jwt = API.currentJwt();

        if (jwt.isPresent()) {
            this.postHandleAuthenticated(request, response, handler, modelAndView);
        }
    }
}
