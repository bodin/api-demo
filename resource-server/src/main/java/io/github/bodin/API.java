package io.github.bodin;

import io.github.bodin.subscription.SubscriptionLevel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

public class API {

    public static Optional<JwtAuthenticationToken> current(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtt) {
            return Optional.of(jwtt);
        }
        return Optional.empty();
    }

    public static Optional<Jwt> currentJwt(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtt) {
            if(jwtt.getPrincipal() instanceof Jwt jwt){
                return Optional.of(jwt);
            }
        }
        return Optional.empty();
    }
    public static SubscriptionLevel getSubscription(){
       return SubscriptionLevel.lookup(claim("client_subscription"));
    }

    public static String getClientId(){
        return claim("client_id");
    }

    public static String claim(String c){
        return currentJwt()
                .map(jwt -> claim(jwt, c))
                .orElse(null);
    }
    public static String claim(Jwt jwt, String c){
        if(c == null) return null;

        Object result = jwt.getClaims().get(c);
        if(result == null) return null;

        return result.toString();
    }

    public static boolean scope(String s){
        return current()
                .map(jwt -> scope(jwt, s))
                .orElse(false);
    }
    public static boolean scope(JwtAuthenticationToken jwt, String s){
        if(s == null) return false;
        String check = "SCOPE_" + s;
        Object result = jwt.getAuthorities().stream()
                .filter(authority -> authority.getAuthority().equalsIgnoreCase(check))
                .findFirst()
                .orElse(null);

        return result != null;
    }
}
