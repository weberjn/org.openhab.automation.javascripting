package org.openhab.automation.javascripting.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Repeatable(ItemStateChangeTriggers.class)
@Target(ElementType.FIELD)
public @interface ItemStateChangeTrigger {
    String id();

    String item();

    String newState() default "";

    String previousState() default "";
}
