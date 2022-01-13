# Java Scripting

This openHAB add-on provides support for Java JSR 223 scripts.

It makes heavy use of Eric Obermühlner's Java JSR 223 ScriptEngine [java-scriptengine](https://github.com/eobermuhlner/java-scriptengine)
which is included here in a hacked copy.


Currently this is pre-Alpha.

What works:

Java in default package.

```java

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Script {

	private static final Logger logger = LoggerFactory.getLogger("org.openhab.core.automation.javarules");
	
	static Map<String, Object> m;

	public void setBindings(Map<String, Object> m) {
		this.m = m;
	}

	public Map<String, Object> getBindings() { 
		return m;
	}

	
	public static int counter = 1;
 

	public static void main(String[] args)  {
		
		logger.info("Hello java world!");
		logger.info("counter: " + counter++);
		
		logger.info("Java main start");
		
		logger.info("bindings: " + m);

		System.out.println("Java main end");
    }
}

```

# Testing

put Script.java into conf/automation/jsr223/

The Java class is loaded, compiled into memory and it's main method is executed.

# addon project  for scripts

Create a folder in the openhab addons bundle tree, copy the pom.xml of another binding, 
remove everything but the parent, change groupId and artifactId.

Import this as maven project into Eclipse.

link conf/automation/jsr223 as external source folder.


