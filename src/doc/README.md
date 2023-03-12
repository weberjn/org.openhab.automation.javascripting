#set( $H2 = '##' )

# openHAB Java Scripting

This openHAB add-on provides support for JSR 223 scripts written in Java.

It makes heavy use of Eric Obermühlner's Java JSR 223 ScriptEngine [java-scriptengine](https://github.com/eobermuhlner/java-scriptengine).

# Programming Hints

* all Java classes used as JSR 223 script have to inherit from [org.openhab.automation.javascripting.scriptsupport.Script](src/main/java/org/openhab/automation/javascripting/scriptsupport/Script.java)

* When the openHAB ScriptFileWatcher detects a new .java File in conf/automation/jsr223 
  it is loaded, compiled into memory, parsed for @Rule annotations and the rules are activated. Then the onLoad() method is executed.

* Java Rules do not see other rule classes. Each one has its own ClassLoader. 

* you can use libraries if you package them as [OSGI bundles](#library-code).

* you can use openHAB classes from the packages listed in [bnd.bnd](bnd.bnd).

* openHAB Java Scripting requires openHAB 3.3.0 or later

# Test

* Copy org.openhab.automation.javascripting-3.3.0.jar into the addons folder (download via the [Releases](https://github.com/weberjn/org.openhab.automation.javascripting/releases) link).

* Copy from the sample Java classes into conf/automation/jsr223/

(they are all in src/test/java)

A Java class is loaded, compiled into memory and its onLoad() method executed. A Python or JS Script is
evalated during load, this is simulated with the onLoad() method. So, rules can be defined programmatically
in onLoad().
Or, you can annotate public instance variables of type SimpleRule. See the CronRule sample.

# Project for Scripts

To have a script compile without errors in Eclipse, it should be in a Java project with openHAB dependencies and a dependency to javascripting.

* create a folder in the openhab addons bundle tree
* copy the pom.xml of a binding 
* remove everything in the pom but the parent
* change groupId and artifactId
* import the folder as maven project into Eclipse
* create the Java scripts in src/main/java in the default package 
* if the source compiles without errors, copy it to conf/automation/jsr223


# Library Code 

Java Rules has `DynamicImport-Package: *` so it can access code in other bundles. 

Bundle your code as OSGI bundle as in this sample: https://github.com/weberjn/org.openhab.automation.javascripting.ext 

# Building the Addon

Get and mvn install java-scriptengine (you have to symlink ch.obermuhlner.scriptengine.java/src to make the Maven
build work).

Clone Java Scripting under openhab-addons/bundles and run mvn install

# Sample Scripts

The samples are all in [src/test/java](src/test/java).

${H2} Item change rules, annotation based

```java
#include("src/test/java/MPDSilencer.java")
```

${H2} Changing Items

```java
#include("src/test/java/EventBusExamples.java")
```

${H2} Cron Rule, annotation based

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
It does not use syntactic sugar of the Script base class, only pure openHAB JSR 223.

```java
#include("src/test/java/GroovyPort.java")
```

