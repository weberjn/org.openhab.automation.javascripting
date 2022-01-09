# Java Scripting

This openHAB add-on provides support for Java JSR 223 scripts.

It makes heavy use of Eric Obermühlner's Java JSR 223 ScriptEngine [java-scriptengine](https://github.com/eobermuhlner/java-scriptengine)
which is included here in a hacked copy.


Currently this is pre-Alpha.

What works:

Java in default package and without library dependencies.

```java

import java.util.Map;
import java.io.*;

public class Script {
	
	static Map<String, Object> m;

	public void setBindings(Map<String, Object> m) {
		this.m = m;
	}

	public Map<String, Object> getBindings() {
		return m;
	}

	public static void main(String[] args)  {
		
		System.out.println("Hello from Java");
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


