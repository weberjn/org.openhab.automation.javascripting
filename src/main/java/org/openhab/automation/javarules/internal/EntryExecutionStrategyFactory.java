package org.openhab.automation.javarules.internal;

import java.lang.reflect.Method;

import javax.script.ScriptException;

import ch.obermuhlner.scriptengine.java.execution.ExecutionStrategy;
import ch.obermuhlner.scriptengine.java.execution.ExecutionStrategyFactory;

public class EntryExecutionStrategyFactory implements ExecutionStrategyFactory {

    @Override
    public ExecutionStrategy create(Class<?> clazz) throws ScriptException {

        return new ExecutionStrategy() {

            @Override
            public Object execute(Object instance) throws ScriptException {
                Object o;
                try {
                    Method m = instance.getClass().getMethod("main", String[].class);

                    String[] args = {};

                    Object[] argswrapper = new Object[1];
                    argswrapper[0] = args;
                    m.invoke(null, argswrapper);
                } catch (Exception e) {
                    throw new ScriptException(e);
                }

                return null;
            }
        };
    }
}
