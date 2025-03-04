package io.github.bodin.subscription;

public enum ApiScope {

    Api1("api_api1"), Api2("api_api1"), Api3("api_api1");

    private final String scope;

    ApiScope(String scope) {
        this.scope = scope;
    }
    public String getScope() {
        return scope;
    }
}
