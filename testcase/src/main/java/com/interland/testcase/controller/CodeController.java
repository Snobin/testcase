package com.interland.testcase.controller;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api/code")
@RestController
public class CodeController {

    @PostMapping("/evaluate")
    public CompilerResult compileAndTest(@RequestBody String codeRequest) {
        CompilerResult result = new CompilerResult();

        try {
            // Compile the code
            StringWriter compilerOutput = new StringWriter();
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
            StringWriter codeWriter = new StringWriter();
            codeWriter.write(codeRequest);

            JavaFileObject javaFileObject = new JavaSourceFromString("DynamicClass", codeWriter.toString());
            Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObject);

            JavaCompiler.CompilationTask task = compiler.getTask(compilerOutput, fileManager, diagnostics, null, null, compilationUnits);
            boolean success = task.call();

            // Execute the compiled code and test
            if (success) {
                StringWriter executionOutput = new StringWriter();
                PrintWriter printWriter = new PrintWriter(executionOutput);
                ClassLoader classLoader = new ByteArrayClassLoader(codeRequest.getBytes());
                Class<?> clazz = classLoader.loadClass("DynamicClass");
                Runnable runnable = (Runnable) clazz.newInstance();

                // Redirect System.out to capture output
//                System.setOut(new PrintStream(printWriter));
                runnable.run();
                System.setOut(System.out); // Reset System.out


                result.setOutput(executionOutput.toString());
                result.setSuccess(true);
            } else {
                result.setErrorMessage(compilerOutput.toString());
                result.setSuccess(false);
            }

            fileManager.close();
        } catch (Exception e) {
            result.setErrorMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }

    private static class ByteArrayClassLoader extends ClassLoader {
        private final Map<String, byte[]> classes;

        public ByteArrayClassLoader(Map<String, byte[]> classes) {
            this.classes = classes;
        }

        public ByteArrayClassLoader(byte[] bytes) {
			this.classes = null;
			// TODO Auto-generated constructor stub
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

    private static class JavaSourceFromString extends SimpleJavaFileObject {
        private final String code;

        public JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }

    public static class CompilerResult {
        private boolean success;
        private String output;
        private String errorMessage;

        // Constructors, getters, and setters...

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getOutput() {
            return output;
        }

        public void setOutput(String output) {
            this.output = output;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    public static class CodeRequest {
        private String code;

        // Constructors, getters, and setters...

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
