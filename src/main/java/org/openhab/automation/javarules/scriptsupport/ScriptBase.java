package org.openhab.automation.javarules.scriptsupport;

import java.lang.reflect.Method;
import java.util.Map;

import org.openhab.core.automation.module.script.rulesupport.shared.ScriptedAutomationManager;
import org.openhab.core.thing.binding.ThingActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ScriptBase {

    private static Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules");

    private Map<String, Object> bindings;

    protected ScriptedAutomationManager automationManager;

    protected ScriptThingActionsProxy actions;

    protected ScriptExtensionManagerWrapperProxy self;

    // ScriptExtensionManagerWrapper is in the bundle private
    // org.openhab.core.automation.module.script.internal.ScriptExtensionManagerWrapper
    // that we can only access by reflection (Java is not Groovy)

    protected static class ScriptExtensionManagerWrapperProxy {

        Object scriptExtensionManagerRef;

        ScriptExtensionManagerWrapperProxy(Object scriptExtensionManagerRef) {
            this.scriptExtensionManagerRef = scriptExtensionManagerRef;
        }

        @SuppressWarnings("unchecked")
        public Map<String, Object> importPreset(String preset) {

            Object o;
            Map<String, Object> presets = null;

            try {
                Method m = scriptExtensionManagerRef.getClass().getMethod("importPreset", String.class);
                o = m.invoke(scriptExtensionManagerRef, preset);
                presets = (Map<String, Object>) o;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return presets;
        }
    }

    protected static class ScriptThingActionsProxy {

        Object scriptThingActionsRef;

        ScriptThingActionsProxy(Object scriptThingActionsRef) {
            this.scriptThingActionsRef = scriptThingActionsRef;
        }

        public ThingActions get(String scope, String thingUid) {

            Object o;
            ThingActions actions = null;

            try {
                Method m = scriptThingActionsRef.getClass().getMethod("get", String.class, String.class);
                o = m.invoke(scriptThingActionsRef, scope, thingUid);
                actions = (ThingActions) o;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return actions;
        }

        public void invoke(ThingActions thingActions, String method, Object... params) {
            Object o;

            Class<?>[] paramClasses = new Class<?>[params.length];

            for (int i = 0; i < params.length; i++) {
                paramClasses[i] = params[i].getClass();
            }
            try {

                Method m = thingActions.getClass().getMethod(method, paramClasses);
                o = m.invoke(thingActions, params);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
     * framework calls this on script load
     */

    public Map<String, Object> eval(Map<String, Object> bindings) {
        this.bindings = bindings;

        Object se = bindings.get("se");

        self = new ScriptExtensionManagerWrapperProxy(se);

        Object actions = bindings.get("actions");

        this.actions = new ScriptThingActionsProxy(actions);

        automationManager = (ScriptedAutomationManager) bindings.get("automationManager");

        try {
            onLoad();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

        return this.bindings;
    }

    public void setBindings(Map<String, Object> bindings) {
        this.bindings = bindings;
    }

    protected Map<String, Object> getBindings() {
        return bindings;
    }

    // to be implemented by the Script.java
    protected abstract void onLoad();
}
