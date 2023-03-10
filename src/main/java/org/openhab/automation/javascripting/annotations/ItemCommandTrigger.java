package org.openhab.automation.javascripting.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Repeatable(ItemCommandTriggers.class)
@Target(ElementType.FIELD)
public @interface ItemCommandTrigger {
    String id();

    String item();

    String command();
}
