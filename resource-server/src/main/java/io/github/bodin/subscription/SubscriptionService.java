package io.github.bodin.subscription;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SubscriptionService {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Subscription getSubscription(SubscriptionLevel level) {
        return switch (level) {
            case Free -> new Subscription(level, Bandwidth.classic(20, Refill.intervally(20, Duration.ofHours(1))));
            case Silver -> new Subscription(level, Bandwidth.classic(40, Refill.intervally(40, Duration.ofHours(1))));
            case Gold -> new Subscription(level, Bandwidth.classic(60, Refill.intervally(60, Duration.ofHours(1))));
            case Platinum -> new Subscription(level, Bandwidth.classic(80, Refill.intervally(80, Duration.ofHours(1))));
        };
    }

    public Bucket resolveBucket(String apiKey, SubscriptionLevel subscription) {
        return cache.computeIfAbsent(apiKey, _ -> newBucket(subscription));
    }

    private Bucket newBucket(SubscriptionLevel subscription) {
        Subscription sub = this.getSubscription(subscription);
        return Bucket.builder()
                .addLimit(sub.getBandwidth())
                .build();
    }
}