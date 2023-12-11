package com.interland.testcase.services;

import javax.tools.*;

import java.io.StringWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import com.interland.testcase.dto.CompileResponse;
import com.interland.testcase.dto.InMemoryFileManager;

@Service
public class CompileServiceImpl implements CompilerService{
	
	@Override
	public CompileResponse javaCompiler(String code) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        InMemoryFileManager fileManager = new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));

        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                fileManager,
                null,
                Arrays.asList("-classpath", System.getProperty("java.class.path")),
                null,
                Arrays.asList(new SimpleJavaFileObject(URI.create("string:///Main.java"), JavaFileObject.Kind.SOURCE) {
                    @Override
                    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                        return code;
                    }
                })
        );

        boolean compilationSuccessful = task.call();
        String output = fileManager.getOutput();
        CompileResponse response = new CompileResponse();
        response.setOutput(output);
        response.setCompilationSuccessful(compilationSuccessful);
        return response;
    }
	
	@Override
	public CompileResponse pythonCompiler(String code) {
        StringWriter outputWriter = new StringWriter();
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.setOut(outputWriter);
        interpreter.exec(code);
        String output = outputWriter.toString();
        CompileResponse response = new CompileResponse();
        response.setOutput(output);
        response.setCompilationSuccessful(true);
        return response;
	}
}
