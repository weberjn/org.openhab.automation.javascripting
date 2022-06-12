package org.openhab.automation.javarules.internal;

import java.lang.reflect.Method;
import java.util.Map;

import ch.obermuhlner.scriptengine.java.bindings.BindingStrategy;

public class BulkBindingStrategy implements BindingStrategy {

	@Override
	public void associateBindings(Class<?> compiledClass, Object compiledInstance, Map<String, Object> mergedBindings) {
        Method m;
        try {
            m = compiledClass.getMethod("setBindings", Map.class);

            m.invoke(compiledInstance, mergedBindings);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public Map<String, Object> retrieveBindings(Class<?> compiledClass, Object compiledInstance) {
        Method m;
        
        Map<String, Object> bindings;
        
        try {
            m = compiledClass.getMethod("getBindings");

            Object o = m.invoke(compiledInstance);
            
            bindings = (Map<String, Object>)o ;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

		return bindings;
	}

}
