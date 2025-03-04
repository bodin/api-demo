package io.github.bodin.interceptor;

import io.github.bodin.API;
import io.github.bodin.metrics.MetricsService;
import io.github.bodin.subscription.SubscriptionLevel;
import io.github.bodin.subscription.SubscriptionService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class RateLimitInterceptor extends AuthenticatedMethodHandlerInterceptor {

    @Autowired
    private SubscriptionService pricingPlanService;

    @Override
    public boolean preHandleAuthenticated(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler)
            throws Exception {

        SubscriptionLevel subscription = API.getSubscription();
        String clientId = API.getClientId();

        Bucket tokenBucket = pricingPlanService.resolveBucket(clientId, subscription);
        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        } else {

            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("You have exhausted your API Request Quota");

            metrics.blockedLimit(clientId,"You have exhausted your API Request Quota");
            return false;
        }
    }
}