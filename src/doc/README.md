#set( $H2 = '##' )

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

Put one of the sample Java classes below into conf/automation/jsr223/

The Java class is loaded, compiled into memory and its onLoad() method executed.

# Project  for scripts

To have a script compile without errors in Eclipse, it should be in a Java project with openHAB dependencies.

* create a folder in the openhab addons bundle tree
* copy the pom.xml of a binding 
* remove everything in the pom but the parent
* change groupId and artifactId
* import the folder as maven project into Eclipse
* link conf/automation/jsr223 as external source folder

# Sample Scripts

${H2} Changing Items

```java
#include("src/test/java/EventBusExamples.java")
```

${H2} Cron rule

```java
#include("src/test/java/CronRule.java")
```

${H2} ItemChanged rule

```java
#include("src/test/java/ItemChangedRule.java")
```

${H2} addon actions

```java
#include("src/test/java/SendMail.java")
```

${H2} static actions

```java
#include("src/test/java/StaticActions.java")
```

${H2} Transformations

```java
#include("src/test/java/Transformations.java")
```
${H2} Persistence

```java
#include("src/test/java/PersistItems.java")
```
  
${H2} Groovy port

This class is ported from the [openHAB JSR 223 Groovy Sample](https://www.openhab.org/docs/configuration/jsr223.html#groovy)

```java
#include("src/test/java/GroovyPort.java")
```

