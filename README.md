
# openHAB 3.2 Java Scripting

This openHAB add-on provides support for Java JSR 223 scripts.

It makes heavy use of Eric Obermühlner's Java JSR 223 ScriptEngine [java-scriptengine](https://github.com/eobermuhlner/java-scriptengine)
which is included here in a hacked copy.

Currently this is Beta code.

# Programming hints

All Java classes used as JSR 223 script have to inherit from [org.openhab.automation.javarules.scriptsupport.ScriptBase](src/main/java/org/openhab/automation/javarules/scriptsupport/ScriptBase.java)

Java Rules do not see other rule classes. Each one has its own ClassLoader. You cannot use own library jars, except if you build OSGI bundles.

You can use openHAB classes from the packages listed in [bnd.bnd](bnd.bnd).

The bundle manifest pulls in bundles from openHAB 3.2.0 so javarules only works under 3.2.0

# Test

Take from the sample Java classes below and put them into conf/automation/jsr223/

(they are all in src/test/java)

The Java class is loaded, compiled into memory and its onLoad() method executed.

# Project  for scripts

To have a script compile without errors in Eclipse, it should be in a Java project with openHAB dependencies and a dependency to javarules, of course.

* create a folder in the openhab addons bundle tree
* copy the pom.xml of a binding 
* remove everything in the pom but the parent
* change groupId and artifactId
* import the folder as maven project into Eclipse
* link conf/automation/jsr223 as external source folder

# Sample Scripts

## Changing Items

```java

import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.items.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusExamples extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.eventbus");

    @Override
    protected void onLoad() {

        events.sendCommand("Livingroom_Light", "OFF");

        Item item = itemRegistry.get("Morning_Temperature");
        events.postUpdate(item, 37.2f);

        Number state = (Number) item.getState();
        logger.info("new State: {}", state.floatValue());

        logger.info("eventbus done");
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

        logger.info("BatteryLevelChanged rule activated");
    };
}
```

## addon actions

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

## static actions

```java

import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.model.script.actions.Exec;
import org.openhab.core.model.script.actions.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticActions extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.actions");

    @Override
    protected void onLoad() {

        String res = HTTP.sendHttpGetRequest("http://localhost/");

        String cmd = "termux-media-player play camera-shutter.mp3";
        Exec.executeCommandLine("ssh", "nexus9", cmd);

        logger.info("static actions done");
    }
}
```

## Transformations

```java

import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.transform.actions.Transformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transformations extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.transform");

    @Override
    protected void onLoad() {

        String s = Transformation.transform("REGEX", ".*(hello).*", "hello, world");

        logger.info("transform done, got: " + s);
    }
}
```
## Persistence

```java

import org.openhab.automation.javarules.scriptsupport.ScriptBase;
import org.openhab.core.items.Item;
import org.openhab.core.persistence.extensions.PersistenceExtensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistItems extends ScriptBase {

    private Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules.persist");

    @Override
    protected void onLoad() {

        Item item = itemRegistry.get("Morning_Temperature");

        PersistenceExtensions.persist(item);

        logger.info("persist done");
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
    }
}
```

