
# openHAB 3.2 Java Scripting

This openHAB add-on provides support for Java JSR 223 scripts.

It makes heavy use of Eric Obermühlner's Java JSR 223 ScriptEngine [java-scriptengine](https://github.com/eobermuhlner/java-scriptengine)
which is included here in a hacked copy.


Currently this is Beta code.


All Java classes used as JSR 223 script have to inherit from [org.openhab.automation.javarules.scriptsupport.ScriptBase](src/main/java/org/openhab/automation/javarules/scriptsupport/ScriptBase.java)

# Test

put Script.java into conf/automation/jsr223/

The Java class is loaded, compiled into memory and its onLoad() method is executed.

# addon project  for scripts

* Create a folder in the openhab addons bundle tree
* copy the pom.xml of another binding, 
* remove everything but the parent
* change groupId and artifactId.
* Import this as maven project into Eclipse.
* link conf/automation/jsr223 as external source folder.

# Sample Scripts

## Changing Items

```java

import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.items.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusExamples extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.command");

    @Override
    protected void onLoad() {

        events.sendCommand("Livingroom_Light", "OFF");

        Item item = itemRegistry.get("Morning_Temperature");
        events.postUpdate(item, 37.2f);

        logger.info("command sent");
    }
}
```

## Cron rule

```java

import java.util.Map;

import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.automation.Action;
import org.openhab.core.automation.Trigger;
import org.openhab.core.automation.module.script.rulesupport.shared.simple.SimpleRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CronRule extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.cronrule");

    public int counter = 1;

    @Override
    protected void onLoad() {

        SimpleRule sr = new SimpleRule() {

            @Override
            public Object execute(Action module, Map<String, ?> inputs) {

                logger.info("Java cronrule execute {}", counter++);

                return null;
            }
        };

        Trigger trigger = createGenericCronTrigger("CronRuleTrigger", "0 * * * * ?");

        ruleBuilder(sr).withName("CronRule").withTrigger(trigger).activate();
    };
}
```

## ItemChanged rule

```java

import java.util.Map;

import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.automation.Action;
import org.openhab.core.automation.Trigger;
import org.openhab.core.automation.module.script.rulesupport.shared.simple.SimpleRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemChangedRule extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.itemrule");

    public int counter = 1;

    @Override
    protected void onLoad() {

        SimpleRule sr = new SimpleRule() {

            @Override
            public Object execute(Action module, Map<String, ?> inputs) {

                logger.info("Java cronrule execute {}", counter++);

                return null;
            }
        };

        Trigger trigger = createItemStateChangeTrigger("BatteryLevelChangedTrigger", "BatteryLevel");

        ruleBuilder(sr).withName("BatteryLevelChanged").withTrigger(trigger).activate();
    };
}
```

## an action

```java

import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.thing.binding.ThingActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendMail extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.mail");

    @Override
    protected void onLoad() {

        ThingActions thingActions = actions.get("mail", "mail:smtp:mailSender");
        actions.invoke(thingActions, "mail_at_receiver", "a subject", "mailconten Java script onload()");

        logger.info("mail sent");
    }
}
```
 
## Groovy port

This class is ported from the [openHAB JSR 223 Groovy Sample](https://www.openhab.org/docs/configuration/jsr223.html#groovy)

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.automation.Action;
import org.openhab.core.automation.Trigger;
import org.openhab.core.automation.module.script.rulesupport.shared.simple.SimpleRule;
import org.openhab.core.automation.util.TriggerBuilder;
import org.openhab.core.config.core.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * @author weberjn
 */
public class GroovyPort extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.script");

    public int counter = 1;

    protected void onLoad() {
        try {

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
            logger.trace(e.getMessage(), e);

            throw e;
        }
    }
}
```
