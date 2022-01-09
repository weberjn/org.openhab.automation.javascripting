package ch.obermuhlner.scriptengine.java;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Stream;

/**
 * A {@link ClassLoader} that loads classes from memory.
 */
public class MemoryClassLoader extends ClassLoader {

    /**
     * URL used to identify the {@link CodeSource} of the {@link ProtectionDomain} used by this class loader.
     *
     * This is useful to identify classes loaded by this class loader in a policy file.
     * 
     * <pre>
    grant codeBase "jrt:/ch.obermuhlner.scriptengine.java/memory-class" {
    permission java.lang.RuntimePermission "exitVM";
    };
     * </pre>
     */
    public static final String MEMORY_CLASS_URL = "http://ch.obermuhlner/ch.obermuhlner.scriptengine.java/memory-class";

    private ProtectionDomain protectionDomain;
    private Map<String, byte[]> mapClassBytes;

    /**
     * Creates a {@link MemoryClassLoader}.
     *
     * @param mapClassBytes the map of class names to compiled classes
     * @param parent the parent {@link ClassLoader}
     */
    public MemoryClassLoader(Map<String, byte[]> mapClassBytes, ClassLoader parent) {
        super(parent);
        this.mapClassBytes = mapClassBytes;

        try {
            URL url = new URL(MEMORY_CLASS_URL);
            CodeSource codeSource = new CodeSource(url, (Certificate[]) null);
            protectionDomain = new ProtectionDomain(codeSource, null, this, new Principal[0]);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        byte[] bytes = mapClassBytes.get(name);
        if (bytes == null) {
            return super.loadClass(name);
        }

        return defineClass(name, bytes, 0, bytes.length, protectionDomain);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return super.getName();
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // TODO Auto-generated method stub
        return super.loadClass(name, resolve);
    }

    @Override
    protected Object getClassLoadingLock(String className) {
        // TODO Auto-generated method stub
        return super.getClassLoadingLock(className);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // TODO Auto-generated method stub
        return super.findClass(name);
    }

    @Override
    protected Class<?> findClass(String moduleName, String name) {
        // TODO Auto-generated method stub
        return super.findClass(moduleName, name);
    }

    @Override
    protected URL findResource(String moduleName, String name) throws IOException {
        // TODO Auto-generated method stub
        return super.findResource(moduleName, name);
    }

    @Override
    public URL getResource(String name) {
        // TODO Auto-generated method stub
        return super.getResource(name);
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        // TODO Auto-generated method stub
        return super.getResources(name);
    }

    @Override
    public Stream<URL> resources(String name) {
        // TODO Auto-generated method stub
        return super.resources(name);
    }

    @Override
    protected URL findResource(String name) {
        // TODO Auto-generated method stub
        return super.findResource(name);
    }

    @Override
    protected Enumeration<URL> findResources(String name) throws IOException {
        // TODO Auto-generated method stub
        return super.findResources(name);
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        // TODO Auto-generated method stub
        return super.getResourceAsStream(name);
    }

    @Override
    protected Package definePackage(String name, String specTitle, String specVersion, String specVendor,
            String implTitle, String implVersion, String implVendor, URL sealBase) {
        // TODO Auto-generated method stub
        return super.definePackage(name, specTitle, specVersion, specVendor, implTitle, implVersion, implVendor,
                sealBase);
    }

    @Override
    protected Package getPackage(String name) {
        // TODO Auto-generated method stub
        return super.getPackage(name);
    }

    @Override
    protected Package[] getPackages() {
        // TODO Auto-generated method stub
        return super.getPackages();
    }

    @Override
    protected String findLibrary(String libname) {
        // TODO Auto-generated method stub
        return super.findLibrary(libname);
    }

    @Override
    public void setDefaultAssertionStatus(boolean enabled) {
        // TODO Auto-generated method stub
        super.setDefaultAssertionStatus(enabled);
    }

    @Override
    public void setPackageAssertionStatus(String packageName, boolean enabled) {
        // TODO Auto-generated method stub
        super.setPackageAssertionStatus(packageName, enabled);
    }

    @Override
    public void setClassAssertionStatus(String className, boolean enabled) {
        // TODO Auto-generated method stub
        super.setClassAssertionStatus(className, enabled);
    }

    @Override
    public void clearAssertionStatus() {
        // TODO Auto-generated method stub
        super.clearAssertionStatus();
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }
}
