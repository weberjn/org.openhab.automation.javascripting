package org.openhab.automation.javarules.internal;

import java.io.Reader;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

import ch.obermuhlner.scriptengine.java.JavaScriptEngine;

public class JavaRulesScriptEngine implements ScriptEngine {

    private JavaScriptEngine delegate;

    JavaRulesScriptEngine(JavaScriptEngine delegate) {
        this.delegate = delegate;
    }

    public Object eval(String script, ScriptContext context) throws ScriptException {
        return delegate.eval(script, context);
    }

    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        return delegate.eval(reader, context);
    }

    public Object eval(String script) throws ScriptException {
        return delegate.eval(script);
    }

    public Object eval(Reader reader) throws ScriptException {
        // openHAB entry

        Object o = delegate.eval(reader);
        return o;
    }

    public Object eval(String script, Bindings n) throws ScriptException {
        return delegate.eval(script, n);
    }

    public Object eval(Reader reader, Bindings n) throws ScriptException {
        return delegate.eval(reader, n);
    }

    public void put(String key, Object value) {
        delegate.put(key, value);
    }

    public Object get(String key) {
        return delegate.get(key);
    }

    public Bindings getBindings(int scope) {
        return delegate.getBindings(scope);
    }

    public void setBindings(Bindings bindings, int scope) {
        delegate.setBindings(bindings, scope);
    }

    public Bindings createBindings() {
        return delegate.createBindings();
    }

    public ScriptContext getContext() {
        return delegate.getContext();
    }

    public void setContext(ScriptContext context) {
        delegate.setContext(context);
    }

    public ScriptEngineFactory getFactory() {
        return delegate.getFactory();
    }
}
