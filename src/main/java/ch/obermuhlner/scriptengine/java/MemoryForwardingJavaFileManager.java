package ch.obermuhlner.scriptengine.java;

import static javax.tools.StandardLocation.CLASS_PATH;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;

import org.openhab.automation.javarules.internal.PackageResourceLister;

import ch.obermuhlner.scriptengine.java.util.CompositeIterator;

public class MemoryForwardingJavaFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private final Map<String, ClassMemoryJavaFileObject> mapNameToClasses = new HashMap<>();
    private final ClassLoader parentClassLoader;
    private PackageResourceLister packageLister;

    public MemoryForwardingJavaFileManager(StandardJavaFileManager fileManager, ClassLoader parentClassLoader,
            PackageResourceLister packageLister) {
        super(fileManager);
        this.parentClassLoader = parentClassLoader;
        this.packageLister = packageLister;
    }

    @Override
    public String toString() {
        return "MemoryForwardingJavaFileManager [parentClassLoader=" + parentClassLoader + "]";
    }

    private Collection<ClassMemoryJavaFileObject> memoryClasses() {
        return mapNameToClasses.values();
    }

    public JavaFileObject createSourceFileObject(Object origin, String name, String code) {
        return new MemoryJavaFileObject(origin, name, JavaFileObject.Kind.SOURCE, code);
    }

    @Override
    public ClassLoader getClassLoader(JavaFileManager.Location location) {
        ClassLoader classLoader = super.getClassLoader(location);

        // if (location.isOutputLocation()) { // == CLASS_OUTPUT
        if (parentClassLoader != null) {
            classLoader = parentClassLoader;
        }

        Map<String, byte[]> mapNameToBytes = new HashMap<>();

        for (ClassMemoryJavaFileObject outputMemoryJavaFileObject : memoryClasses()) {
            mapNameToBytes.put(outputMemoryJavaFileObject.getName(), outputMemoryJavaFileObject.getBytes());
        }

        return new MemoryClassLoader(mapNameToBytes, classLoader);
        // }

        // return classLoader;
    }

    @Override
    public Iterable<JavaFileObject> list(Location location, String packageName, Set<Kind> kinds, boolean recurse)
            throws IOException {
        Iterable<JavaFileObject> list = super.list(location, packageName, kinds, recurse);

        if (location.isOutputLocation()) { // == CLASS_OUTPUT
            Collection<? extends JavaFileObject> generatedClasses = memoryClasses();
            return () -> new CompositeIterator<JavaFileObject>(list.iterator(), generatedClasses.iterator());
        } else if (location == CLASS_PATH) {
            Collection<String> c = packageLister.listResources(packageName);

            List<JavaFileObject> classPathClasses = new ArrayList<JavaFileObject>(c.size());

            for (String resource : c) {
                JavaFileObject javaFileObject = new ClasspathMemoryJavaFileObject(parentClassLoader, resource);
                classPathClasses.add(javaFileObject);
            }
            return classPathClasses;
        }

        // location StandardLocation (id=227) CLASS_PATH
        // packageName "org.slf4j" (id=325)
        return list;
    }

    @Override
    public String inferBinaryName(JavaFileManager.Location location, JavaFileObject file) {
        if (file instanceof AbstractMemoryJavaFileObject) {
            return file.getName();
        } else {
            return super.inferBinaryName(location, file);
        }
    }

    @Override
    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className,
            JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        if (kind == JavaFileObject.Kind.CLASS) {
            ClassMemoryJavaFileObject file = new ClassMemoryJavaFileObject(className);
            mapNameToClasses.put(className, file);
            return file;
        }

        return super.getJavaFileForOutput(location, className, kind, sibling);
    }

    static class ClasspathMemoryJavaFileObject extends AbstractMemoryJavaFileObject {

        byte[] byteCode;
        String className;

        ClasspathMemoryJavaFileObject(ClassLoader classLoader, String resource) throws IOException {
            super(resource, JavaFileObject.Kind.CLASS);

            className = resource.substring(0, resource.lastIndexOf("."));
            className = className.replace('/', '.');

            URL url = classLoader.getResource(resource);

            try (InputStream is = url.openStream()) {
                byteCode = is.readAllBytes();
            }
        }

        @Override
        public String getName() {
            return className;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return new String(byteCode, StandardCharsets.UTF_8);
        }

        @Override
        public InputStream openInputStream() {
            return new ByteArrayInputStream(byteCode);
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            return super.openOutputStream();
        }
    }

    static abstract class AbstractMemoryJavaFileObject extends SimpleJavaFileObject {
        public AbstractMemoryJavaFileObject(String name, JavaFileObject.Kind kind) {
            super(URI.create("memory:///" + name.replace('.', '/') + kind.extension), kind);
        }
    }

    static class MemoryJavaFileObject extends AbstractMemoryJavaFileObject {
        private final Object origin;
        private final String code;

        MemoryJavaFileObject(Object origin, String className, JavaFileObject.Kind kind, String code) {
            super(className, kind);

            this.origin = origin;
            this.code = code;
        }

        public Object getOrigin() {
            return origin;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }

    static class ClassMemoryJavaFileObject extends AbstractMemoryJavaFileObject {

        private ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        private transient byte[] bytes = null;

        private final String className;

        public ClassMemoryJavaFileObject(String className) {
            super(className, JavaFileObject.Kind.CLASS);

            this.className = className;
        }

        public byte[] getBytes() {
            if (bytes == null) {
                bytes = byteOutputStream.toByteArray();
                byteOutputStream = null;
            }
            return bytes;
        }

        @Override
        public String getName() {
            return className;
        }

        @Override
        public OutputStream openOutputStream() {
            return byteOutputStream;
        }

        @Override
        public InputStream openInputStream() {
            return new ByteArrayInputStream(getBytes());
        }
    }
}
