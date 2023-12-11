// CodeExecutionController.java
package com.interland.testcase.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.interland.testcase.config.ApplicationProperties;
import com.interland.testcase.services.UnzipUtility;

@RestController
public class CodeController extends PythonInterpreter {
	 private final Logger log = LoggerFactory.getLogger(CodeController.class);
//	@Autowired
//	 private ApplicationProperties applicationProperties;
//	  @Autowired
//	    private UnzipUtility unzipUtility;

	@PostMapping("/execute/python")

	public String executePythonCode(@RequestBody String code) throws IOException, InterruptedException {
        // Create a StringWriter to capture the output
        StringWriter outputWriter = new StringWriter();

        // Create a PythonInterpreter
        PythonInterpreter interpreter = new PythonInterpreter();

        // Redirect the standard output to the StringWriter
        interpreter.setOut(outputWriter);

        // Execute the Python code
        interpreter.exec(code);

        // Retrieve the output from the StringWriter
        String output = outputWriter.toString();

        // Print the output (for testing purposes)
        System.out.println("Python Output: " + output);

        // Now you can use the 'output' variable for further purposes
        return output;
    }

    @PostMapping("/execute/java")
    public String executeJavaCode(@RequestBody String code) {
        try {
            // Save the code to a file (you may use a unique filename)
            String className = "DynamicClass";
            String fileName = className + ".java";
            writeCodeToFile(code, fileName);

            // Create a StringWriter to capture the compilation output
            StringWriter compilationOutput = new StringWriter();

            // Compile the Java code
            if (compileJavaCode(fileName, compilationOutput)) {
                // Execute the compiled code
                return executeCompiledCode(className);
            } else {
                return "Compilation failed. Error message:\n" + compilationOutput.toString();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private void writeCodeToFile(String code, String fileName) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(code);
        }
    }

    private boolean compileJavaCode(String fileName, Writer outputWriter) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(fileName);

            JavaCompiler.CompilationTask task = compiler.getTask(outputWriter, fileManager, diagnostics, null, null, compilationUnits);
            return task.call();
        }
    }

    private String executeCompiledCode(String className) throws Exception {
        // Create a custom class loader to load the compiled class
        ByteArrayClassLoader classLoader = new ByteArrayClassLoader();

        // Load the compiled class
        Class<?> dynamicClass = classLoader.loadClass(className);

        // Create an instance and invoke the main method
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        dynamicClass.getMethod("main", String[].class).invoke(null, (Object) new String[]{});

        // Reset System.out to the original PrintStream
        System.setOut(originalOut);

        return "Output:\n" + output.toString();
    }

    private static class ByteArrayClassLoader extends ClassLoader {
        private final Map<String, byte[]> classes;

        public ByteArrayClassLoader() {
            this.classes = Map.of();
        }

        public ByteArrayClassLoader(Map<String, byte[]> classes) {
            this.classes = classes;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] classBytes = classes.get(name);
            if (classBytes == null) {
                throw new ClassNotFoundException(name);
            }

            return defineClass(name, classBytes, 0, classBytes.length);
        }
    }
}