#set( $H2 = '##' )

# openHAB 3.2 Java Scripting

This openHAB add-on provides support for Java JSR 223 scripts.

It makes heavy use of Eric Obermühlner's Java JSR 223 ScriptEngine [java-scriptengine](https://github.com/eobermuhlner/java-scriptengine)
which is included here in a hacked copy.


Currently this is Alpha code.

What works:

The sample Java classes below.

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



${H2} Cron rule

```java
#include("src/test/java/CronRule.java")
```

${H2} ItemChanged rule

```java
#include("src/test/java/ItemChangedRule.java")
```

${H2} an action

```java
#include("src/test/java/SendMail.java")
```
 
${H2} Groovy port

This class is ported from the [openHAB JSR 223 Groovy Sample](https://www.openhab.org/docs/configuration/jsr223.html#groovy)

```java
#include("src/test/java/GroovyPort.java")
```
