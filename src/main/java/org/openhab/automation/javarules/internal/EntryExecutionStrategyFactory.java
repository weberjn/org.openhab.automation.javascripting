package org.openhab.automation.javarules.internal;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptException;

import org.openhab.automation.javarules.scriptsupport.ScriptBase;

import ch.obermuhlner.scriptengine.java.execution.ExecutionStrategy;
import ch.obermuhlner.scriptengine.java.execution.ExecutionStrategyFactory;

public class EntryExecutionStrategyFactory implements ExecutionStrategyFactory {

    @Override
    public ExecutionStrategy create(Class<?> clazz) throws ScriptException {

        return new ExecutionStrategy() {

            @Override
            public Entry<Object, Map<String, Object>> execute(Object instance, Map<String, Object> bindings)
                    throws ScriptException {
                try {

                    ScriptBase script = (ScriptBase) instance;

                    Map<String, Object> sret = script.eval(bindings);

                    Entry<Object, Map<String, Object>> e = new AbstractMap.SimpleEntry<>(null, sret);

                    return e;

                } catch (Exception e) {
                    throw new ScriptException(e);
                }
            }
        };
    }
}
