package io.github.bodin.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class LocalClientRepository implements RegisteredClientRepository {
    private final Map<String, ApiClient> clients = new HashMap<>();

    private final PasswordEncoder encoder;

    public LocalClientRepository(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public void delete(String id) {
        this.clients.remove(id);
    }

    public List<ApiClient> getAll() {
        return this.clients.values().stream().toList();
    }

    public NewClient add(String name, OffsetDateTime expires, String subscription, Collection<String> scopes) {
        String secret = UUID.randomUUID().toString();
        RegisteredClient.Builder cb = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(UUID.randomUUID().toString())
                .clientName(name)
                .clientSecret(this.encoder.encode(secret))
                .clientSecretExpiresAt(expires.toInstant())

                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS);

        scopes.forEach(cb::scope);

        ApiClient c = new ApiClient(cb.build());
        c.setSubscription(subscription);

        this.clients.put(c.getId(), c);

        return new NewClient(c.getId(), c.getClientId(), secret);
    }


    @Override
    public void save(RegisteredClient c) {
        ApiClient ac = new ApiClient(c);
        if (this.clients.containsKey(c.getId())) {
            ac.setSubscription(this.clients.get(c.getId()).getSubscription());
        }
        this.clients.put(c.getId(), ac);
    }

    public ApiClient lookup(String id) {
        if (!this.clients.containsKey(id)) return null;

        return this.clients.get(id);
    }

    @Override
    public RegisteredClient findById(String id) {
        if (!this.clients.containsKey(id)) return null;

        return this.clients.get(id).toRegisteredClient();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return this.clients.values().stream()
                .filter(c -> c.getClientId().equals(clientId))
                .findFirst()
                .map(ApiClient::toRegisteredClient)
                .orElse(null);
    }
}
