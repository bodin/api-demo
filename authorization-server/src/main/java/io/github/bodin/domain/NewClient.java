package io.github.bodin.domain;

import java.io.Serializable;

public class NewClient implements Serializable {
    private String id;
    private String clientId;
    private String clientSecret;

    public NewClient() {}

    public NewClient(String id, String clientId, String clientSecret) {
        this.id = id;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
