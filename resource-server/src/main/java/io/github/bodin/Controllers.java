package io.github.bodin;

import io.github.bodin.annotation.Scope;
import io.github.bodin.annotation.Subscription;
import io.github.bodin.subscription.ApiScope;
import io.github.bodin.subscription.SubscriptionLevel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controllers {

    @Scope(ApiScope.Api1)
    @Subscription({SubscriptionLevel.Free, SubscriptionLevel.Silver, SubscriptionLevel.Gold, SubscriptionLevel.Platinum})
    @GetMapping("/v1/free")
    public String free(@AuthenticationPrincipal Jwt jwt) {
        return String.format("[FREE] Hello [%s] %s | Subscription %s", jwt.getClaims().get("client_id"), jwt.getClaims().get("client_name"), jwt.getClaims().get("client_subscription"));
    }

    @Scope(ApiScope.Api2)
    @Subscription({SubscriptionLevel.Silver, SubscriptionLevel.Gold, SubscriptionLevel.Platinum})
    @GetMapping("/v1/silver")
    public String silver(@AuthenticationPrincipal Jwt jwt) {
        return String.format("[SILVER] Hello [%s] %s | Subscription %s", jwt.getClaims().get("client_id"), jwt.getClaims().get("client_name"), jwt.getClaims().get("client_subscription"));
    }

    @Scope(ApiScope.Api2)
    @Subscription({SubscriptionLevel.Gold, SubscriptionLevel.Platinum})
    @GetMapping("/v1/gold")
    public String gold(@AuthenticationPrincipal Jwt jwt) {
        return String.format("[GOLD] Hello [%s] %s | Subscription %s", jwt.getClaims().get("client_id"), jwt.getClaims().get("client_name"), jwt.getClaims().get("client_subscription"));
    }

    @Scope(ApiScope.Api3)
    @Subscription({SubscriptionLevel.Platinum})
    @GetMapping("/v1/platinum")
    public String platinum(@AuthenticationPrincipal Jwt jwt) {
        return String.format("[PLATINUM] Hello [%s] %s | Subscription %s", jwt.getClaims().get("client_id"), jwt.getClaims().get("client_name"), jwt.getClaims().get("client_subscription"));
    }
}