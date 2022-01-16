package ch.obermuhlner.scriptengine.java.execution;

import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptException;

/**
 * The strategy used to execute a method on an object instance.
 */
public interface ExecutionStrategy {
    /**
     * Executes a method on an object instance, or a static method if the specified instance is {@code null}.
     *
     * @param instance the object instance to be executed or {@code null} to execute a static method
     * @return the return value of the method, or {@code null}
     * @throws ScriptException if no method to execute was found
     */
    Entry<Object, Map<String, Object>> execute(Object instance, Map<String, Object> bindings) throws ScriptException;
}
