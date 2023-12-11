package com.interland.testcase.services;

import javax.tools.*;

import org.springframework.stereotype.Service;

import com.interland.testcase.dto.CompileResponse;
import com.interland.testcase.dto.InMemoryFileManager;

import java.net.URI;
import java.util.Arrays;

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
        System.out.println(output);
        return response;
    }
}
