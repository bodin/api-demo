package io.github.bodin.domain;

import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.time.Instant;
import java.util.Set;

public class ApiClient {

    private RegisteredClient client;
    private String subscription;

    public ApiClient(RegisteredClient client) {
        this.client = client;
    }

    public RegisteredClient toRegisteredClient() {
        return this.client;
    }

    public String getId() {
        return client.getId();
    }

    public String getClientId() {
        return client.getClientId();
    }

    @Nullable
    public Instant getClientSecretExpiresAt() {
        return client.getClientSecretExpiresAt();
    }

    public Set<String> getScopes() {
        return client.getScopes();
    }

    @Nullable
    public String getClientSecret() {
        return client.getClientSecret();
    }

    public String getClientName() {
        return client.getClientName();
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }
}
