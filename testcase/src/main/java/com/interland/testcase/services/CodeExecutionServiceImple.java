package com.interland.testcase.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.python.core.CompileMode;
import org.python.core.CompilerFlags;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.interland.testcase.dto.CodeRequest;
import com.interland.testcase.dto.CodeResponse;
import com.interland.testcase.entity.CodeExecutionResult;
import com.interland.testcase.entity.QuestionEntity;
import com.interland.testcase.entity.TestCaseEntity;
import com.interland.testcase.repository.codQuestionRepository;
import com.interland.testcase.repository.codeExecutionResultRepository;

@Service
public class CodeExecutionServiceImple implements CodeExecutionService {

	private static final Logger LOGGER = Logger.getLogger(CodeExecutionServiceImple.class.getName());
	@Autowired
	private codQuestionRepository questionRepository;
	@Autowired
	private codeExecutionResultRepository codeExecutionResultRepository;

	@Override
	public List<CodeResponse> executeCode(CodeRequest codeRequest) throws IOException {
		String code = codeRequest.getCode();
		String language = codeRequest.getLangId();

		String questionId = codeRequest.getQnId();

		Optional<QuestionEntity> optionalQuestionEntity = questionRepository.findByQuestionId(questionId);

		if (optionalQuestionEntity.isPresent()) {
			List<TestCaseEntity> testCases = optionalQuestionEntity.get().getTestCases();

			List<String> inputs = testCases.stream().map(TestCaseEntity::getInputs).collect(Collectors.toList());
			List<String> expectedOutputs = testCases.stream().map(TestCaseEntity::getExpectedOutputs)
					.collect(Collectors.toList());

			List<CodeResponse> codeResponses;

			switch (language) {
			case "java":
				codeResponses = executeJavaCode(code, inputs, expectedOutputs);
				saveResultsInDatabase(codeResponses, codeRequest, testCases, expectedOutputs);
				return codeResponses;

			case "c":
				codeResponses = executeCCode(code, inputs, expectedOutputs);
				saveResultsInDatabase(codeResponses, codeRequest, testCases, expectedOutputs);
				return codeResponses;

			case "cpp":
				codeResponses = executeCppCode(code, inputs, expectedOutputs);
				saveResultsInDatabase(codeResponses, codeRequest, testCases, expectedOutputs);
				return codeResponses;

			case "python":
				codeResponses = executePythonCode(code, inputs);
				saveResultsInDatabase(codeResponses, codeRequest, testCases, expectedOutputs);
				return codeResponses;

			default:
				LOGGER.warning("Unsupported language: " + language);
				CodeResponse codeResponse = new CodeResponse();
				codeResponse.setOutput("Unsupported language: " + language);
				codeResponses = executeCppCode(code, inputs, expectedOutputs);
				saveResultsInDatabase(codeResponses, codeRequest, testCases, expectedOutputs);
				return codeResponses;
			}
		} else {
			LOGGER.warning("Question not found for id: " + questionId);
			return Collections.emptyList();
		}
	}

	private List<CodeResponse> executeJavaCode(String code, List<String> inputElements, List<String> expectedOutputs) {
		List<CodeResponse> codeResponses = new ArrayList<>();

		for (int i = 0; i < inputElements.size(); i++) {
			String input = inputElements.get(i);
			String expectedOutput = expectedOutputs.get(i);

			try {
				String className = getClassName(code);
				File tempFile = createTempFile(className, ".java", code);
				String dockerVolumePath = "/tmp";

				ProcessBuilder processBuilder;

				if (input != null && !input.isEmpty()) {
					processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
							tempFile.getParent() + ":" + dockerVolumePath, "openjdk:latest", "bash", "-c",
							"javac " + dockerVolumePath + "/" + tempFile.getName() + " && cd " + dockerVolumePath
									+ " && echo '" + input + "' | timeout 20s java " + className);
				} else {
					processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
							tempFile.getParent() + ":" + dockerVolumePath, "openjdk:latest", "bash", "-c",
							"javac " + dockerVolumePath + "/" + tempFile.getName() + " && cd " + dockerVolumePath
									+ " && timeout 20s java " + className);
				}

				CodeResponse codeResponse = executeProcess(processBuilder, tempFile);

				if (codeResponse.getOutput().trim().equals(expectedOutput.trim())) {
					codeResponse.setSuccess("true");
					codeResponse.setMessage("Test case passed!");
				} else {
					codeResponse.setSuccess("false");
					codeResponse.setMessage(
							"Test case failed. Expected: " + expectedOutput + ", Actual: " + codeResponse.getOutput());
				}

				codeResponses.add(codeResponse);
				if ("Error: Execution timed out after 10 seconds".equals(codeResponse.getOutput())) {
					break;
				}
			} catch (Exception e) {
				LOGGER.severe("Error executing Java code: " + e.getMessage());

				CodeResponse codeResponse = new CodeResponse();
				codeResponse.setOutput("Error: " + e.getMessage());
				codeResponse.setMessage("Test case failed. Error: " + e.getMessage());
				codeResponses.add(codeResponse);
			}
		}

		return codeResponses;
	}

	private List<CodeResponse> executeCCode(String code, List<String> inputElements, List<String> expectedOutputs) {
		List<CodeResponse> codeResponses = new ArrayList<>();

		for (int i = 0; i < inputElements.size(); i++) {
			String input = inputElements.get(i);
			String expectedOutput = expectedOutputs.get(i);

			try {
				File tempFile = createTempFile("code", ".c", code);
				String dockerVolumePath = "/tmp";

				ProcessBuilder processBuilder;
				if (input != null && !input.isEmpty()) {
					processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
							tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "timeout", "20s",
							"bash", "-c",
							"gcc " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath
									+ "/a.out && cd " + dockerVolumePath + " && echo '" + input + "' | ./a.out");
				} else {
					processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
							tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "timeout", "20s",
							"bash", "-c", "gcc " + dockerVolumePath + "/" + tempFile.getName() + " -o "
									+ dockerVolumePath + "/a.out && cd " + dockerVolumePath + " && ./a.out");
				}

				CodeResponse codeResponse = executeProcess(processBuilder, tempFile);

				if (codeResponse.getOutput().trim().equals(expectedOutput.trim())) {
					codeResponse.setSuccess("true");
					codeResponse.setMessage("Test case passed!");
				} else {
					codeResponse.setSuccess("false");
					codeResponse.setMessage(
							"Test case failed. Expected: " + expectedOutput + ", Actual: " + codeResponse.getOutput());
				}

				codeResponses.add(codeResponse);
				if ("Error: Execution timed out after 10 seconds".equals(codeResponse.getOutput())) {
					break;
				}
			} catch (Exception e) {
				LOGGER.severe("Error executing C code: " + e.getMessage());

				CodeResponse codeResponse = new CodeResponse();
				codeResponse.setOutput("Error: " + e.getMessage());
				codeResponse.setSuccess("false");
				codeResponse.setMessage("Test case failed. Error: " + e.getMessage());
				codeResponses.add(codeResponse);
			}
		}

		return codeResponses;
	}

	private List<CodeResponse> executeCppCode(String code, List<String> inputElements, List<String> expectedOutputs) {
		List<CodeResponse> codeResponses = new ArrayList<>();

		for (int i = 0; i < inputElements.size(); i++) {
			String input = inputElements.get(i);
			String expectedOutput = expectedOutputs.get(i);

			try {
				File tempFile = createTempFile("code", ".cpp", code);
				String dockerVolumePath = "/tmp";

				ProcessBuilder processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
						tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "timeout", "20s",
						"bash", "-c", "g++ " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath
								+ "/a.out" + " && cd " + dockerVolumePath + " && echo '" + input + "' | ./a.out");

				CodeResponse codeResponse = executeProcess(processBuilder, tempFile);

				if (codeResponse.getOutput().trim().equals(expectedOutput.trim())) {
					codeResponse.setSuccess("true");
					codeResponse.setMessage("Test case passed!");
				} else {
					codeResponse.setSuccess("false");
					codeResponse.setMessage(
							"Test case failed. Expected: " + expectedOutput + ", Actual: " + codeResponse.getOutput());
				}

				codeResponses.add(codeResponse);
				if ("Error: Execution timed out after 10 seconds".equals(codeResponse.getOutput())) {
					break;
				}
			} catch (Exception e) {
				LOGGER.severe("Error executing C++ code: " + e.getMessage());

				CodeResponse codeResponse = new CodeResponse();
				codeResponse.setOutput("Error: " + e.getMessage());
				codeResponse.setSuccess("false");
				codeResponse.setMessage("Test case failed. Error: " + e.getMessage());
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
				long startTime = System.currentTimeMillis();

				try {
					StringWriter outputWriter = new StringWriter();
					interpreter.setOut(outputWriter);
					interpreter.set("element", inputElement);

					PyCode compiledCode = Py.compile_flags(code, "<string>", CompileMode.exec, new CompilerFlags());
					interpreter.exec(compiledCode);

					codeResponse.setOutput(outputWriter.toString());
				} catch (Exception e) {
					codeResponse.setOutput("Error: " + e.getMessage());
				}

				long endTime = System.currentTimeMillis();
				long elapsedTime = endTime - startTime;

				codeResponse.setProcessingTime(elapsedTime);

				codeResponses.add(codeResponse);
			}
		} catch (Exception e) {
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
					executeAndCaptureOutput(processBuilder, codeResponse, tempFile);
				} catch (Exception e) {
					handleTimeoutError(codeResponse);
				}
			}, null);

			future.get(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			handleExecutionError(codeResponse);
		} finally {
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			codeResponse.setProcessingTime(elapsedTime);

			executorService.shutdownNow();
		}

		return codeResponse;
	}

	private void executeAndCaptureOutput(ProcessBuilder processBuilder, CodeResponse codeResponse, File tempFile)
			throws IOException, InterruptedException {
		processBuilder.redirectErrorStream(true);
		Process process = processBuilder.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder output = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			output.append(line);
		}

		tempFile.delete();

		codeResponse.setOutput(output.toString());
	}

	private void handleTimeoutError(CodeResponse codeResponse) {
		LOGGER.warning("Execution timed out after 10 seconds");
		codeResponse.setOutput("Error: Execution timed out after 10 seconds");
	}

	private void handleExecutionError(CodeResponse codeResponse) {
		LOGGER.severe("Error executing process");
		codeResponse.setOutput("Error: Execution timed out after 10 seconds");
		codeResponse.setSuccess("false");
		codeResponse.setMessage("Test case failed. Error: Execution timed out after 10 seconds");
	}

	private File createTempFile(String prefix, String suffix, String code) throws IOException {
		File tempFile = File.createTempFile(prefix, suffix);
		try (FileWriter fileWriter = new FileWriter(tempFile)) {
			writeCodeToFile(code, fileWriter);
		}
		return tempFile;
	}

	private void writeCodeToFile(String code, FileWriter fileWriter) throws IOException {
		fileWriter.write(code);
	}

	private String getClassName(String code) {
		return extractClassNameFromCode(code);
	}

	private String extractClassNameFromCode(String code) {
		return code.replaceAll("(?s).*?\\bclass\\s+(\\w+).*", "$1");
	}

	public void saveResultsInDatabase(List<CodeResponse> codeResponses, CodeRequest codeRequest,
			List<TestCaseEntity> testCases, List<String> expectedOutputs) {
		List<String> inputs = testCases.stream().map(TestCaseEntity::getInputs).collect(Collectors.toList());

		for (int i = 0; i < inputs.size(); i++) {
			CodeResponse codeResponse = codeResponses.get(i);
			String input = inputs.get(i);
			String expectedOutput = expectedOutputs.get(i);

//			CodeExecutionResult result = createCodeExecutionResult(codeRequest, codeResponse, input, expectedOutput);
//
//			try {
//				codeExecutionResultRepository.save(result);
//			} catch (DataIntegrityViolationException e) {
//				handleSaveResultError(e);
//			}
		}
	}

	private CodeExecutionResult createCodeExecutionResult(CodeRequest codeRequest, CodeResponse codeResponse,
			String input, String expectedOutput) {
		CodeExecutionResult result = new CodeExecutionResult();
		result.setStudentId(codeRequest.getStudentId());
		result.setOutput(codeResponse.getOutput());
		result.setExpectedOutput(expectedOutput);
		result.setTestcase(input);
		result.setQuestionId(codeRequest.getQnId());
		result.setCode(codeRequest.getCode());
		result.setProcessingTime(codeResponse.getProcessingTime());
		result.setLanguageId(codeRequest.getLangId());
		result.setSuccess(codeResponse.getSuccess());
		return result;
	}

	private void handleSaveResultError(DataIntegrityViolationException e) {
		System.out.println("Error saving result: " + e.getMessage());
	}

}
