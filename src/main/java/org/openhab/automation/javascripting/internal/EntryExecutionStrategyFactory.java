package org.openhab.automation.javascripting.internal;

import javax.script.ScriptException;

import org.openhab.automation.javascripting.scriptsupport.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.obermuhlner.scriptengine.java.execution.ExecutionStrategy;
import ch.obermuhlner.scriptengine.java.execution.ExecutionStrategyFactory;

public class EntryExecutionStrategyFactory implements ExecutionStrategyFactory {

    private static Logger logger = LoggerFactory.getLogger(EntryExecutionStrategyFactory.class);

    @Override
    public ExecutionStrategy create(Class<?> clazz) throws ScriptException {

        return new ExecutionStrategy() {

            @Override
            public Object execute(Object instance) throws ScriptException {
                try {
                    if (instance instanceof Script) {
                        Script script = (Script) instance;

                        script.eval();
                    } else {
                        logger.info("cannot execute: {} not instance of ScriptBase",
                                instance.getClass().getSimpleName());
                    }
                    return null;

                } catch (Exception e) {
                    throw new ScriptException(e);
                }
            }
        };
    }
}
