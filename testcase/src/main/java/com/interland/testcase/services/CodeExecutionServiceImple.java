package com.interland.testcase.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.interland.testcase.dto.CodeRequest;
import com.interland.testcase.dto.CodeResponse;

@Service
public class CodeExecutionServiceImple implements CodeExecutionService {

	private static final Logger LOGGER = Logger.getLogger(CodeExecutionServiceImple.class.getName());

	@Override

	public CodeResponse executeCode(CodeRequest codeRequest) throws IOException {

		String code = codeRequest.getCode();
		String language = codeRequest.getLangId();
		String input = codeRequest.getInput();

		switch (language) {
        case "java":
            return executeJavaCode(code, input);
        case "c":
            return executeCCode(code, input);
        case "cpp":
            return executeCppCode(code, input);
        case "python":
            return executePythonCode(code, input);
        default:
            LOGGER.warning("Unsupported language: " + language);
            CodeResponse codeResponse = new CodeResponse();
            codeResponse.setOutput("Unsupported language: " + language);
            return codeResponse;
    }
	}

	private CodeResponse executeJavaCode(String code, String input) {

	    try {
	        File tempFile = createTempFile("abc", ".java", code);
	        String dockerVolumePath = "/tmp";
	        String className = getClassName(code);

	        ProcessBuilder processBuilder;

	        if (input != null && !input.isEmpty()) {
	            // If input is provided, use echo to pass input to the Java program
	            processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
	                    tempFile.getParent() + ":" + dockerVolumePath, "openjdk:latest", "bash", "-c",
	                    "javac " + dockerVolumePath + "/" + tempFile.getName() + " && cd " + dockerVolumePath +
	                            " && echo '" + input + "' | java " + className);
	        } else {
	            // If no input is provided, run the Java program directly
	            processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
	                    tempFile.getParent() + ":" + dockerVolumePath, "openjdk:latest", "bash", "-c",
	                    "javac " + dockerVolumePath + "/" + tempFile.getName() + " && cd " + dockerVolumePath +
	                            " && java " + className);
	        }

	        return executeProcess(processBuilder, tempFile);
	    } catch (Exception e) {
	        LOGGER.severe("Error executing Java code: " + e.getMessage());
	        CodeResponse codeResponse = new CodeResponse();
	        codeResponse.setOutput("Error: " + e.getMessage());
	        return codeResponse;
	    }
	}
	private CodeResponse executeCCode(String code, String input) {
	    try {
	        File tempFile = createTempFile("code", ".c", code);
	        String dockerVolumePath = "/tmp";

	        ProcessBuilder processBuilder;
	        if (input != null && !input.isEmpty()) {
	            // If input is provided, use echo to pass input to the C program
	            processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
	                    tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "bash", "-c",
	                    "gcc " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath + "/a.out && cd "
	                            + dockerVolumePath + " && echo '" + input + "' | ./a.out");
	        } else {
	            // If no input is provided, run the C program directly
	            processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
	                    tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "bash", "-c",
	                    "gcc " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath + "/a.out && cd "
	                            + dockerVolumePath + " && ./a.out");
	        }

	        return executeProcess(processBuilder, tempFile);
	    } catch (Exception e) {
	        LOGGER.severe("Error executing C code: " + e.getMessage());

	        CodeResponse codeResponse = new CodeResponse();
	        codeResponse.setOutput("Error: " + e.getMessage());
	        return codeResponse;
	    }
	}


	private CodeResponse executeCppCode(String code, String input) {
	    try {
	        File tempFile = createTempFile("code", ".cpp", code);
	        String dockerVolumePath = "/tmp";

	        ProcessBuilder processBuilder;
	        if (input != null && !input.isEmpty()) {
	            // If input is provided, use echo to pass input to the C++ program
	            processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
	                    tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "bash", "-c",
	                    "g++ " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath + "/a.out && cd "
	                            + dockerVolumePath + " && echo '" + input + "' | ./a.out");
	        } else {
	            // If no input is provided, run the C++ program directly
	            processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
	                    tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "bash", "-c",
	                    "g++ " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath + "/a.out && cd "
	                            + dockerVolumePath + " && ./a.out");
	        }

	        return executeProcess(processBuilder, tempFile);
	    } catch (Exception e) {
	        LOGGER.severe("Error executing C++ code: " + e.getMessage());

	        CodeResponse codeResponse = new CodeResponse();
	        codeResponse.setOutput("Error: " + e.getMessage());
	        return codeResponse;
	    }
	}


	public static CodeResponse executePythonCode(String code, String input) {
        CodeResponse codeResponse = new CodeResponse();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", "-c", code);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            if (input != null && !input.isEmpty()) {
                try (OutputStream outputStream = process.getOutputStream()) {
                    outputStream.write(input.getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                }
            }

            try (InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
                 BufferedReader reader = new BufferedReader(inputStreamReader)) {

                StringWriter outputWriter = new StringWriter();
                String line;
                while ((line = reader.readLine()) != null) {
                    outputWriter.write(line);
                    outputWriter.write("\n");
                }

                codeResponse.setOutput(outputWriter.toString());
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                codeResponse.setOutput("Error: Python process exited with code " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            codeResponse.setOutput("Error: " + e.getMessage());
        }

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

			future.get(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			LOGGER.severe("Error executing process: " + e.getMessage());
			codeResponse.setOutput("Error: " + e.getMessage());
		} finally {
			long endTime = System.currentTimeMillis();
			long elapsedTime = (endTime - startTime);
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
