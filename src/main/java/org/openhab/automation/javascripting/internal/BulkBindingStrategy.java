package org.openhab.automation.javascripting.internal;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.openhab.automation.javascripting.scriptsupport.Script;

import ch.obermuhlner.scriptengine.java.bindings.BindingStrategy;

public class BulkBindingStrategy implements BindingStrategy {

    @Override
    public void associateBindings(Class<?> compiledClass, Object compiledInstance, Map<String, Object> mergedBindings) {
        Method m;
        try {
            if (compiledInstance instanceof Script) {
                m = compiledClass.getMethod("setBindings", Map.class);

                m.invoke(compiledInstance, mergedBindings);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> retrieveBindings(Class<?> compiledClass, Object compiledInstance) {
        Method m;

        Map<String, Object> bindings = null;

        try {
            if (compiledInstance instanceof Script) {

                m = compiledClass.getMethod("getBindings");

                Object o = m.invoke(compiledInstance);

                bindings = (Map<String, Object>) o;
            } else {
                bindings = new HashMap<String, Object>();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return bindings;
    }
}
