package org.openhab.automation.javascripting.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Repeatable(ChannelEventTriggers.class)
@Target(ElementType.FIELD)
public @interface ChannelEventTrigger {
    String id();

    String channelUID();

    String event() default "";
}
