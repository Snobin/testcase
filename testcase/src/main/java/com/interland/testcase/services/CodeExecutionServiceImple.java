package com.interland.testcase.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.python.core.CompileMode;
import org.python.core.CompilerFlags;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import com.interland.testcase.dto.CodeRequest;
import com.interland.testcase.dto.CodeResponse;

@Service
public class CodeExecutionServiceImple implements CodeExecutionService {

	private static final Logger LOGGER = Logger.getLogger(CodeExecutionServiceImple.class.getName());

	@Override

	public List<CodeResponse> executeCode(CodeRequest codeRequest) throws IOException {

		String code = codeRequest.getCode();
		String language = codeRequest.getLangId();
		String input = codeRequest.getInput();
		List<String> ele = codeRequest.getElements();

		switch (language) {
		case "java":
			return executeJavaCode(code, ele);
		case "c":
			return executeCCode(code, ele);
		case "cpp":
			return executeCppCode(code, ele);
		case "python":
			return executePythonCode(code, ele);
		default:
			LOGGER.warning("Unsupported language: " + language);
			CodeResponse codeResponse = new CodeResponse();
			codeResponse.setOutput("Unsupported language: " + language);
//            return codeResponse;
			return executeCppCode(code, ele);
		}
	}

	private List<CodeResponse> executeJavaCode(String code, List<String> inputElements) {
		List<CodeResponse> codeResponses = new ArrayList<>();

		for (String input : inputElements) {
			try {
				String className = getClassName(code);
				File tempFile = createTempFile(className, ".java", code);

				String dockerVolumePath = "/tmp";

				ProcessBuilder processBuilder;

				if (input != null && !input.isEmpty()) {
					// If input is provided, use echo to pass input to the Java program
					processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
							tempFile.getParent() + ":" + dockerVolumePath, "openjdk:latest", "bash", "-c",
							"javac " + dockerVolumePath + "/" + tempFile.getName() + " && cd " + dockerVolumePath
									+ " && echo '" + input + "' | timeout 20s java " + className);
				} else {
					// If no input is provided, run the Java program directly
					processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
							tempFile.getParent() + ":" + dockerVolumePath, "openjdk:latest", "bash", "-c",
							"javac " + dockerVolumePath + "/" + tempFile.getName() + " && cd " + dockerVolumePath
									+ " && timeout 20s java " + className);
				}

				CodeResponse codeResponse = executeProcess(processBuilder, tempFile);
				System.out.println(codeResponse.getOutput());

				codeResponses.add(codeResponse);
				if ("Error: Execution timed out after 10 seconds".equals(codeResponse.getOutput())) {
					break;
				}
			} catch (Exception e) {
				LOGGER.severe("Error executing Java code: " + e.getMessage());

				CodeResponse codeResponse = new CodeResponse();
				codeResponse.setOutput("Error: " + e.getMessage());
				codeResponses.add(codeResponse);
			}
		}

		return codeResponses;
	}

	private List<CodeResponse> executeCCode(String code, List<String> inputElements) {
		List<CodeResponse> codeResponses = new ArrayList<>();

		for (String input : inputElements) {
			try {
				File tempFile = createTempFile("code", ".c", code);
				String dockerVolumePath = "/tmp";

				ProcessBuilder processBuilder;
				if (input != null && !input.isEmpty()) {
					// If input is provided, use echo to pass input to the C program
					processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
							tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "timeout", "20s", // Adjust
																														// timeout
																														// as
																														// needed
							"bash", "-c",
							"gcc " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath
									+ "/a.out && cd " + dockerVolumePath + " && echo '" + input + "' | ./a.out");
				} else {
					// If no input is provided, run the C program directly
					processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
							tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "timeout", "20s", // Adjust
																														// timeout
																														// as
																														// needed
							"bash", "-c", "gcc " + dockerVolumePath + "/" + tempFile.getName() + " -o "
									+ dockerVolumePath + "/a.out && cd " + dockerVolumePath + " && ./a.out");
				}

				CodeResponse codeResponse = executeProcess(processBuilder, tempFile);
				System.out.println(codeResponse.getOutput());

				codeResponses.add(codeResponse);
				if ("Error: Execution timed out after 10 seconds".equals(codeResponse.getOutput())) {
					break;
				}
			} catch (Exception e) {
				LOGGER.severe("Error executing C code: " + e.getMessage());

				CodeResponse codeResponse = new CodeResponse();
				codeResponse.setOutput("Error: " + e.getMessage());
				codeResponses.add(codeResponse);
			}
		}

		return codeResponses;
	}

	private List<CodeResponse> executeCppCode(String code, List<String> inputElements) {
		List<CodeResponse> codeResponses = new ArrayList<>();

		for (String input : inputElements) {
			try {
				File tempFile = createTempFile("code", ".cpp", code);
				String dockerVolumePath = "/tmp";


				ProcessBuilder processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
						tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "timeout", "20s", 
						"bash", "-c", "g++ " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath
								+ "/a.out" + " && cd " + dockerVolumePath + " && echo '" + input + "' | ./a.out");


				CodeResponse codeResponse = executeProcess(processBuilder, tempFile);
				System.out.println(codeResponse.getOutput());

				codeResponses.add(codeResponse);
				if ("Error: Execution timed out after 10 seconds".equals(codeResponse.getOutput())) {
					break;
				}
			} catch (Exception e) {
				LOGGER.severe("Error executing C++ code: " + e.getMessage());

				CodeResponse codeResponse = new CodeResponse();
				codeResponse.setOutput("Error: " + e.getMessage());
				codeResponses.add(codeResponse);
			}
		}

		return codeResponses;
	}

	public static List<CodeResponse> executePythonCode(String code, List<String> inputElements) {
	    List<CodeResponse> codeResponses = new ArrayList<>();

	    try (PythonInterpreter interpreter = new PythonInterpreter()) {
	        for (String inputElement : inputElements) {
	            CodeResponse codeResponse = new CodeResponse();
	            long startTime = System.currentTimeMillis(); // Record start time

	            try {
	                // Use a StringWriter to capture the output
	                StringWriter outputWriter = new StringWriter();
	                interpreter.setOut(outputWriter);

	                // Set the input element in the Python interpreter
	                interpreter.set("element", inputElement);

	                // Execute the modified Python code
	                PyCode compiledCode = Py.compile_flags(code, "<string>", CompileMode.exec, new CompilerFlags());
	                interpreter.exec(compiledCode);

	                // Capture the output from the StringWriter
	                codeResponse.setOutput(outputWriter.toString());
	            } catch (Exception e) {
	                codeResponse.setOutput("Error: " + e.getMessage());
	            }

	            // Record end time
	            long endTime = System.currentTimeMillis();
	            long elapsedTime = endTime - startTime;

	            // Set processing time
	            codeResponse.setProcessingTime(elapsedTime);

	            // Add the CodeResponse to the list
	            codeResponses.add(codeResponse);
	        }
	    } catch (Exception e) {
	        // Handle Jython initialization error
	        CodeResponse errorResponse = new CodeResponse();
	        errorResponse.setOutput("Error: " + e.getMessage());
	        codeResponses.add(errorResponse);
	    }

	    return codeResponses;
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
			codeResponse.setOutput("Error: Execution timed out after 10 seconds");
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
		// Extract class name using regex

		String className = code.replaceAll("(?s).*?\\bclass\\s+(\\w+).*", "$1");
		return className;
	}

}
