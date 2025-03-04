package io.github.bodin.interceptor;

import io.github.bodin.API;
import io.github.bodin.annotation.Scope;
import io.github.bodin.annotation.Subscription;
import io.github.bodin.metrics.MetricsService;
import io.github.bodin.subscription.ApiScope;
import io.github.bodin.subscription.SubscriptionLevel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

@Component
public class ScopeInterceptor extends AuthenticatedMethodHandlerInterceptor {

    @Override
    public boolean preHandleAuthenticated(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler)
            throws Exception {

        String clientId = API.getClientId();

        Scope annotation = handler.getMethodAnnotation(Scope.class);

        if (annotation == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("Invalid Request, unscoped API");

            metrics.blockedScope(clientId,"You have exhausted your API Request Quota");

            return false;
        }

        ApiScope scope = annotation.value();

        if(!API.scope(scope.getScope())) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter()
                    .write("Requires scope: " + scope.name().toLowerCase());

            response.setContentType("text/plain");

            metrics.blockedScope(clientId,"Requires scope: " + scope.name().toLowerCase());

            return false;
        }

        return true;
    }
}