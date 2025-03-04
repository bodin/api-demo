package io.github.bodin.interceptor;

import io.github.bodin.*;
import io.github.bodin.annotation.Subscription;
import io.github.bodin.annotation.AccessGold;
import io.github.bodin.annotation.AccessPlatinum;
import io.github.bodin.annotation.AccessSilver;
import io.github.bodin.metrics.MetricsService;
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
public class SubscriptionInterceptor extends AuthenticatedMethodHandlerInterceptor {

    @Override
    public boolean preHandleAuthenticated(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler)
            throws Exception {

        String clientId = API.getClientId();
        SubscriptionLevel subscription = API.getSubscription();
        Subscription annotation = handler.getMethodAnnotation(Subscription.class);

        if (annotation == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("Invalid Request, no subscription found");

            metrics.blockedSubscription(clientId,"Invalid Request, no subscription found");

            return false;
        }

        EnumSet<SubscriptionLevel> levels = EnumSet.copyOf(Arrays.asList(annotation.value()));

        if(!levels.contains(subscription)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter()
                    .write("Requires subscription: " + levels.stream()
                            .map(Enum::toString)
                            .collect(Collectors.joining(","))
                    );
            response.setContentType("text/plain");

            metrics.blockedSubscription(clientId,"Requires subscription: " + levels.stream()
                    .map(Enum::toString)
                    .collect(Collectors.joining(",")));

            return false;
        }

        return true;
    }
}