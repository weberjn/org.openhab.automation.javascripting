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
        	public Object execute(Object instance) throws ScriptException {
                try {

                    ScriptBase script = (ScriptBase) instance;

                    script.eval();

                    return null;

                } catch (Exception e) {
                    throw new ScriptException(e);
                }
            }
        };
    }
}
