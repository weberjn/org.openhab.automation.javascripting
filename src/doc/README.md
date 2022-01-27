#set( $H2 = '##' )

# openHAB 3.2 Java Scripting

This openHAB add-on provides support for JSR 223 scripts written in Java.

It makes heavy use of Eric Obermühlner's Java JSR 223 ScriptEngine [java-scriptengine](https://github.com/eobermuhlner/java-scriptengine)
which is included here in a hacked copy.

Currently this is Beta code.

# Programming Hints

* All Java classes used as JSR 223 script have to inherit from [org.openhab.automation.javarules.scriptsupport.ScriptBase](src/main/java/org/openhab/automation/javarules/scriptsupport/ScriptBase.java)

* Java Rules do not see other rule classes. Each one has its own ClassLoader. 

* you can use libraries if you package them as [OSGI bundles](#library-code).

* you can use openHAB classes from the packages listed in [bnd.bnd](bnd.bnd).

* the bundle manifest pulls in bundles from openHAB 3.2.0 so javarules only works under 3.2.0

# Test

* Copy org.openhab.automation.javarules-3.2.0.jar into the addons folder (download via the [Releases](https://github.com/weberjn/org.openhab.automation.javarules/releases) link).

* Copy from the sample Java classes into conf/automation/jsr223/

(they are all in src/test/java)

A Java class is loaded, compiled into memory and its onLoad() method executed.

# Project  for Scripts

To have a script compile without errors in Eclipse, it should be in a Java project with openHAB dependencies and a dependency to javarules, of course.

* create a folder in the openhab addons bundle tree
* copy the pom.xml of a binding 
* remove everything in the pom but the parent
* change groupId and artifactId
* import the folder as maven project into Eclipse
* link conf/automation/jsr223 as external source folder

# Library Code 

Java Rules has `DynamicImport-Package: *` so it can access code in other bundles. 

Bundle your code as OSGI bundle as in this sample: https://github.com/weberjn/org.openhab.automation.javarules.ext 

# Sample Scripts

The samples are all in [src/test/java](src/test/java).

${H2} Changing Items

```java
#include("src/test/java/EventBusExamples.java")
```

${H2} Cron Rule

```java
#include("src/test/java/CronRule.java")
```

${H2} ItemChanged Rule

```java
#include("src/test/java/ItemChangedRule.java")
```

${H2} Addon Actions

```java
#include("src/test/java/SendMail.java")
```

${H2} Static Actions

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
  
${H2} Groovy Port

This class is ported from the [openHAB JSR 223 Groovy Sample](https://www.openhab.org/docs/configuration/jsr223.html#groovy).
It does not use syntactic sugar of ScriptBase, only pure openHAB JSR 223.

```java
#include("src/test/java/GroovyPort.java")
```

