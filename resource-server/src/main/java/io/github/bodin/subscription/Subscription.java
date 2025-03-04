package io.github.bodin.subscription;

import io.github.bucket4j.Bandwidth;

public class Subscription {

    private SubscriptionLevel level;
    private Bandwidth bandwidth;

    public Subscription(SubscriptionLevel level, Bandwidth bandwidth) {
        this.level = level;
        this.bandwidth = bandwidth;
    }

    public SubscriptionLevel getLevel() {
        return level;
    }

    public Bandwidth getBandwidth() {
        return bandwidth;
    }


}
