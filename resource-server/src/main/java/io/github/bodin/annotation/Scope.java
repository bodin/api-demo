package io.github.bodin.annotation;

import io.github.bodin.subscription.ApiScope;
import io.github.bodin.subscription.SubscriptionLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    ApiScope value();
}