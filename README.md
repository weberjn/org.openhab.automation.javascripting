# openHAB 3.3 Java Scripting

This openHAB add-on provides support for Java JSR 223 scripts.

It makes heavy use of Eric Obermühlner's Java JSR 223 ScriptEngine [java-scriptengine](https://github.com/eobermuhlner/java-scriptengine)
which is included here in a hacked copy.


Currently this is Alpha.

What works:

This Script ported from the [openHAB Groovy Sample](https://www.openhab.org/docs/configuration/jsr223.html#groovy)

# Sample Script

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.automation.util.TriggerBuilder;
import org.openhab.core.automation.Action;
import org.openhab.core.automation.Trigger;
import org.openhab.core.automation.module.script.rulesupport.shared.simple.SimpleRule;
import org.openhab.core.config.core.Configuration;
import org.openhab.core.thing.binding.ThingActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 * @author weberjn
 */
public class Script extends ScriptBase {

	private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.script");

	public int counter = 1;
	
	protected void onLoad() {
		try {

			ThingActions thingActions = actions.get("mqtt","mqtt:broker:nico");
			actions.invoke(thingActions, "publishMQTT", "about/java","Java script onload()");

			logger.info("MQTT done");
					
			SimpleRule sr = new SimpleRule() {

				@Override
				public Object execute(Action module, Map<String, ?> inputs) {

					logger.info("Java execute {},  inputs: {} ", counter++, inputs);

					return null;
				}
			};
			
			sr.setName("Java-One");

			List<Trigger> triggers = new ArrayList<Trigger>(1);

			Map<String, Object> triggerConf = new HashMap<String, Object>();
			triggerConf.put("cronExpression", "0 * * * * ?");

			Trigger trigger = TriggerBuilder.create().withId("aTimerTrigger").withTypeUID("timer.GenericCronTrigger")
					.withConfiguration(new Configuration(triggerConf)).build();

			triggers.add(trigger);

			sr.setTriggers(triggers);

			automationManager.addRule(sr);

			logger.info("onLoad() done");
		} catch (Throwable e) {
			e.printStackTrace(System.err);
			logger.trace(e.getMessage(), e);

			throw e;
		}
	}
}

```

# Test

put Script.java into conf/automation/jsr223/

The Java class is loaded, compiled into memory and its onLoad() method is executed.

# addon project  for scripts

Create a folder in the openhab addons bundle tree, copy the pom.xml of another binding, 
remove everything but the parent, change groupId and artifactId.

Import this as maven project into Eclipse.

link conf/automation/jsr223 as external source folder.


