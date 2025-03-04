package io.github.bodin.subscription;

public enum SubscriptionLevel {
    Free, Gold, Silver, Platinum;

    public static SubscriptionLevel lookup(String level) {
        if(level == null) return Free;

        return switch (level.toLowerCase()) {
            case "silver" -> Silver;
            case "gold" -> Gold;
            case "platinum" -> Platinum;
            default -> Free;
        };
    }
}
