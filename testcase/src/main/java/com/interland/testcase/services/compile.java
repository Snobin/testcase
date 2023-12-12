package com.interland.testcase.services;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.springframework.stereotype.Service;

@Service
public class compile implements CompilerService {

    @Override
    public String compileAndRun(String code, String language) throws IOException, InterruptedException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        if ("java".equalsIgnoreCase(language)) {
            return compileAndRunJava(code);
        }
        // Add support for other languages as needed

        return "Language not supported: " + language;
    }

    private String compileAndRunJava(String code) throws IOException, InterruptedException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        // Save the code to a temporary file
        String className = "DynamicClass";
        String fileName = className + ".java";

        // Compile the Java code
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(
                    new DynamicJavaSourceCodeObject(fileName, code)
            );

            CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
            boolean success = task.call();

            if (!success) {
                return "Compilation failed. Error message:\n" + getCompilationErrors(diagnostics);
            }
        }

        // Execute the compiled code
        return executeCompiledCode(className);
    }

    private String getCompilationErrors(DiagnosticCollector<JavaFileObject> diagnostics) {
        StringBuilder errors = new StringBuilder();
        for (var diagnostic : diagnostics.getDiagnostics()) {
            errors.append(diagnostic.getMessage(null)).append("\n");
        }
        return errors.toString();
    }

    private String executeCompiledCode(String className) throws IOException, InterruptedException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        // Use a custom class loader to load the compiled class
//        ByteArrayClassLoader classLoader = new ByteArrayClassLoader();

        // Load the compiled class
//        Class<?> dynamicClass = classLoader.loadClass(className);

        // Redirect System.out to capture the output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // Invoke the main method of the compiled class
//        dynamicClass.getMethod("main", String[].class).invoke(null, (Object) new String[]{});

        // Reset System.out to the original PrintStream
        System.setOut(System.out);

        return "Output:\n" + output.toString(StandardCharsets.UTF_8);
    }

    private static class DynamicJavaSourceCodeObject implements JavaFileObject {
        private final String code;
        private final String name;

        public DynamicJavaSourceCodeObject(String name, String code) {
            this.code = code;
            this.name = name;
        }

        @Override
        public Kind getKind() {
            return Kind.SOURCE;
        }

        @Override
        public boolean isNameCompatible(String simpleName, Kind kind) {
            return simpleName.equals(name);
        }

        @Override
        public NestingKind getNestingKind() {
            return null;
        }

        @Override
        public Modifier getAccessLevel() {
            return Modifier.ABSTRACT; // or the appropriate modifier for your case
        }

        @Override
        public URI toUri() {
            return null;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public InputStream openInputStream() {
            return new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public OutputStream openOutputStream() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Reader openReader(boolean ignoreEncodingErrors) {
            return new StringReader(code);
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }

        @Override
        public Writer openWriter() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getLastModified() {
            return 0;
        }

        @Override
        public boolean delete() {
            return false;
        }

	
    }
}
