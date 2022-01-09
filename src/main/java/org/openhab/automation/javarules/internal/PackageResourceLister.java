package org.openhab.automation.javarules.internal;

import java.util.Collection;

import org.eclipse.jdt.annotation.NonNullByDefault;

@NonNullByDefault
public interface PackageResourceLister {
    Collection<String> listResources(String packageName);
}
