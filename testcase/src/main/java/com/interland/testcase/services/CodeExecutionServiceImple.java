package com.interland.testcase.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.logging.Logger;
import java.util.concurrent.*;

import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import com.interland.testcase.dto.CodeRequest;

import com.interland.testcase.dto.CodeResponse;


@Service
public class CodeExecutionServiceImple implements CodeExecutionService {


	private static final Logger LOGGER = Logger.getLogger(CodeExecutionServiceImple.class.getName());

	@Override

	public CodeResponse executeCode(CodeRequest codeRequest) {

		String code = codeRequest.getCode();
		String language = codeRequest.getLangId();

		switch (language) {
		case "java":
			return executeJavaCode(code);
		case "c":
			return executeCCode(code);
		case "cpp":
			return executeCppCode(code);
		case "python":
			return executePythonCode(code);
		default:
			LOGGER.warning("Unsupported language: " + language);

			CodeResponse codeResponse = new CodeResponse();
			codeResponse.setOutput("Unsupported language: " + language);
			return codeResponse;
		}
	}

	private CodeResponse executeJavaCode(String code) {

		try {
			File tempFile = createTempFile("abc", ".java", code);
			String dockerVolumePath = "/tmp";
			String className = getClassName(code);

			ProcessBuilder processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
					tempFile.getParent() + ":" + dockerVolumePath, "tomcat:latest", "bash", "-c",
					"javac " + dockerVolumePath + "/" + tempFile.getName() + " && cd " + dockerVolumePath + " && java "
							+ className);

			return executeProcess(processBuilder, tempFile);
		} catch (Exception e) {
			LOGGER.severe("Error executing Java code: " + e.getMessage());
			CodeResponse codeResponse = new CodeResponse();
			codeResponse.setOutput("Error: " + e.getMessage());
			return codeResponse;
		}
	}

	private CodeResponse executeCCode(String code) {

		try {
			File tempFile = createTempFile("code", ".c", code);
			String dockerVolumePath = "/tmp";

			ProcessBuilder processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
					tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "bash", "-c",
					"gcc " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath + "/a.out && cd "
							+ dockerVolumePath + " && ./a.out");

			return executeProcess(processBuilder, tempFile);
		} catch (Exception e) {
			LOGGER.severe("Error executing C code: " + e.getMessage());

			CodeResponse codeResponse = new CodeResponse();
			codeResponse.setOutput("Error: " + e.getMessage());
			return codeResponse;
		}
	}

	private CodeResponse executeCppCode(String code) {

		try {
			File tempFile = createTempFile("code", ".cpp", code);
			String dockerVolumePath = "/tmp";

			ProcessBuilder processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
					tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "bash", "-c",
					"g++ " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath + "/a.out && cd "
							+ dockerVolumePath + " && ./a.out");

			return executeProcess(processBuilder, tempFile);
		} catch (Exception e) {
			LOGGER.severe("Error executing C++ code: " + e.getMessage());

			CodeResponse codeResponse = new CodeResponse();
			codeResponse.setOutput("Error: " + e.getMessage());
			return codeResponse;
		}
	}

	private CodeResponse executePythonCode(String code) {
		StringWriter outputWriter = new StringWriter();
		CodeResponse codeResponse = new CodeResponse();

		try (PythonInterpreter interpreter = new PythonInterpreter()) {
			interpreter.setOut(outputWriter);
			interpreter.exec(code);
		}

		String output = outputWriter.toString();
		LOGGER.info("Python Output: " + output);

		codeResponse.setOutput(output);
		return codeResponse;
	}

	private CodeResponse executeProcess(ProcessBuilder processBuilder, File tempFile) {
	    ExecutorService executorService = Executors.newSingleThreadExecutor();
	    CodeResponse codeResponse = new CodeResponse();

	    long startTime = System.currentTimeMillis();

	    try {
	        Future<Void> future = executorService.submit(() -> {
	            try {
	                processBuilder.redirectErrorStream(true);
	                Process process = processBuilder.start();

	                int exitCode = process.waitFor();

	                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	                StringBuilder output = new StringBuilder();
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    output.append(line).append("\n");
	                }

	                tempFile.delete();

	                codeResponse.setOutput(output.toString());
	            } catch (Exception e) {
	            	LOGGER.warning("Execution timed out after 10 seconds");
                	codeResponse.setOutput("Error: Execution timed out after 10 seconds");
	            }
	        }, null);

	        
	        future.get(4, TimeUnit.SECONDS);
	    } catch (Exception e) {
	        LOGGER.severe("Error executing process: " + e.getMessage());
	        codeResponse.setOutput("Error: " + e.getMessage());
	    } finally {
	        long endTime = System.currentTimeMillis();
	        long elapsedTime = (endTime - startTime) / 1000;
	        // Adding processing time to CodeResponse
	        codeResponse.setProcessingTime(elapsedTime);

	        executorService.shutdownNow();
	    }

	    return codeResponse;
	}


	private File createTempFile(String prefix, String suffix, String code) throws Exception {
		File tempFile = File.createTempFile(prefix, suffix);
		try (FileWriter fileWriter = new FileWriter(tempFile)) {
			fileWriter.write(code);
		}
		return tempFile;
	}

	private String getClassName(String code) {
		String className = code.replaceAll("(?s).*?\\bclass\\s+(\\w+).*", "$1");
		return className.trim();
	}

}
