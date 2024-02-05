package com.interland.testcase.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.interland.testcase.dto.CodeRequest;
import com.interland.testcase.dto.CodeResponse;
import com.interland.testcase.entity.CodeExecutionResult;
import com.interland.testcase.entity.CodeResult;
import com.interland.testcase.entity.CodeResultPk;
import com.interland.testcase.entity.QuestionEntity;
import com.interland.testcase.entity.TestCaseEntity;
import com.interland.testcase.repository.CodingResultRepository;
import com.interland.testcase.repository.codQuestionRepository;

@Service
public class CodeExecutionServiceImple implements CodeExecutionService {

	private static final Logger logger = LoggerFactory.getLogger(CodeExecutionServiceImple.class);
	@Autowired
	private codQuestionRepository questionRepository;
	@Autowired
	private CodingResultRepository codingResultRepository;

	@Autowired
	private CombinedResultImplementation combinedResultImplementation;

	Integer passedtestcases = 0;
	ExecutorService executorService = Executors.newFixedThreadPool(5);

	@Override
	public List<CodeResponse> executeCode(CodeRequest codeRequest) throws IOException {

		CodeResult codeResult = new CodeResult();
		CodeResultPk codeResultPk = new CodeResultPk();

		try {
			String code = codeRequest.getCode();
			String language = codeRequest.getLangId();
			String user = codeRequest.getUser();
			String questionId = codeRequest.getQnId();

			Optional<QuestionEntity> optionalQuestionEntity = questionRepository.findByQuestionId(questionId);

			if (optionalQuestionEntity.isPresent()) {
				List<TestCaseEntity> testCases = optionalQuestionEntity.get().getTestCases();

				codeResultPk.setQuestionId(questionId);
				codeResultPk.setUser(user);
				codeResult.setCodeResultpk(codeResultPk);
				codeResult.setCode(code);
				codeResult.setLanguage(language);
				codeResult.setTotalTescase(testCases.size());

				List<String> inputs = testCases.stream().map(TestCaseEntity::getInputs).collect(Collectors.toList());
				List<String> expectedOutputs = testCases.stream().map(TestCaseEntity::getExpectedOutputs)
						.collect(Collectors.toList());

				List<CodeResponse> codeResponses;
				int numberOfTestCasesToExecute = "run".equals(codeRequest.getStatus()) ? 3 : testCases.size();

				switch (language) {
				case "java":
					codeResponses = executeJavaCode(code, inputs, expectedOutputs, numberOfTestCasesToExecute);
					break;

				case "c":
					codeResponses = executeCCode(code, inputs, expectedOutputs, numberOfTestCasesToExecute);
					break;

				case "cpp":
					codeResponses = executeCppCode(code, inputs, expectedOutputs, numberOfTestCasesToExecute);
					break;

				default:
					CodeResponse codeResponse = new CodeResponse();
					codeResponse.setOutput("Unsupported language: " + language);
					codeResponses = Collections.singletonList(codeResponse);
					break;
				}

				saveResultsInDatabase(codeResponses, codeRequest, testCases, expectedOutputs,
						numberOfTestCasesToExecute);
				codeResult.setPassedTestcase(passedtestcases);
				passedtestcases = 0;
				codingResultRepository.save(codeResult);
				combinedResultImplementation.getResult();
				return codeResponses;

			} else {
				return Collections.emptyList();
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);

			return Collections.emptyList();
		}
	}

	private List<CodeResponse> executeJavaCode(String code, List<String> inputElements, List<String> expectedOutputs,
			int numberOfTestCasesToExecute) {
		List<CompletableFuture<CodeResponse>> futures = new ArrayList<>();

		for (int i = 0; i < numberOfTestCasesToExecute; i++) {
			String input = inputElements.get(i);
			String expectedOutput = expectedOutputs.get(i);

			CompletableFuture<CodeResponse> future = CompletableFuture.supplyAsync(() -> {
				try {
					return executeSingleJavaTestCase(code, input, expectedOutput);
				} catch (Exception e) {
					logger.error("Error:" + e.getMessage(), e);
					CodeResponse codeResponse = new CodeResponse();
					codeResponse.setOutput("Error: " + e.getMessage());
					codeResponse.setMessage("Test case failed. Error: " + e.getMessage());
					return codeResponse;
				}
			}, executorService);

			futures.add(future);
		}

		return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
				.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList())).join();
	}

	private CodeResponse executeSingleJavaTestCase(String code, String input, String expectedOutput) {
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
				codeResponse.setInput(input);
				codeResponse.setExpectedOutput(expectedOutput);
				passedtestcases++;
				codeResponse.setMessage("Test case passed!");
				codeResponse.setInput(input);
				codeResponse.setExpectedOutput(expectedOutput);
			} else {
				codeResponse.setSuccess("false");
				codeResponse.setInput(input);
				codeResponse.setExpectedOutput(expectedOutput);
				codeResponse.setMessage(
						"Test case failed. Expected: " + expectedOutput + ", Actual: " + codeResponse.getOutput());
			}

			if ("Error: Execution timed out after 10 seconds".equals(codeResponse.getOutput())) {
				tempFile.delete();
				return codeResponse;
			}

			tempFile.delete();
			return codeResponse;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			CodeResponse codeResponse = new CodeResponse();
			codeResponse.setOutput("Error: " + e.getMessage());
			codeResponse.setMessage("Test case failed. Error: " + e.getMessage());
			return codeResponse;
		}
	}

	private List<CodeResponse> executeCCode(String code, List<String> inputElements, List<String> expectedOutputs,
			int numberOfTestCasesToExecute) {
		List<CompletableFuture<CodeResponse>> futures = new ArrayList<>();

		for (int i = 0; i < numberOfTestCasesToExecute; i++) {
			String input = inputElements.get(i);
			String expectedOutput = expectedOutputs.get(i);

			CompletableFuture<CodeResponse> future = CompletableFuture.supplyAsync(() -> {
				try {
					return executeSingleCTestCase(code, input, expectedOutput);
				} catch (Exception e) {
					logger.error("Error:" + e.getMessage(), e);
					CodeResponse codeResponse = new CodeResponse();
					codeResponse.setOutput("Error: " + e.getMessage());
					codeResponse.setMessage("Test case failed. Error: " + e.getMessage());
					return codeResponse;
				}
			}, executorService);

			futures.add(future);
		}

		return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
				.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList())).join();
	}

	private CodeResponse executeSingleCTestCase(String code, String input, String expectedOutput) {
		try {
			File tempFile = createTempFile("code", ".c", code);
			String dockerVolumePath = "/tmp";

			ProcessBuilder processBuilder;
			if (input != null && !input.isEmpty()) {
				processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
						tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "timeout", "20s",
						"bash", "-c", "gcc " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath
								+ "/a.out && cd " + dockerVolumePath + " && echo '" + input + "' | ./a.out");
			} else {
				processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
						tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "timeout", "20s",
						"bash", "-c", "gcc " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath
								+ "/a.out && cd " + dockerVolumePath + " && ./a.out");
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

			if ("Error: Execution timed out after 10 seconds".equals(codeResponse.getOutput())) {
				tempFile.delete();
				return codeResponse;
			}

			tempFile.delete();
			return codeResponse;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			CodeResponse codeResponse = new CodeResponse();
			codeResponse.setOutput("Error: " + e.getMessage());
			codeResponse.setMessage("Test case failed. Error: " + e.getMessage());
			return codeResponse;
		}
	}

	private List<CodeResponse> executeCppCode(String code, List<String> inputElements, List<String> expectedOutputs,
			int numberOfTestCasesToExecute) {
		List<CompletableFuture<CodeResponse>> futures = new ArrayList<>();

		for (int i = 0; i < numberOfTestCasesToExecute; i++) {
			String input = inputElements.get(i);
			String expectedOutput = expectedOutputs.get(i);

			CompletableFuture<CodeResponse> future = CompletableFuture.supplyAsync(() -> {
				try {
					return executeSingleCppTestCase(code, input, expectedOutput);
				} catch (Exception e) {
					logger.error("Error:" + e.getMessage(), e);
					CodeResponse codeResponse = new CodeResponse();
					codeResponse.setOutput("Error: " + e.getMessage());
					codeResponse.setMessage("Test case failed. Error: " + e.getMessage());
					return codeResponse;
				}
			}, executorService);

			futures.add(future);
		}

		return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
				.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList())).join();
	}

	private CodeResponse executeSingleCppTestCase(String code, String input, String expectedOutput) {
		try {
			File tempFile = createTempFile("code", ".cpp", code);
			String dockerVolumePath = "/tmp";

			ProcessBuilder processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
					tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "timeout", "20s", "bash",
					"-c", "g++ " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath + "/a.out"
							+ " && cd " + dockerVolumePath + " && echo '" + input + "' | ./a.out");

			CodeResponse codeResponse = executeProcess(processBuilder, tempFile);

			if (codeResponse.getOutput().trim().equals(expectedOutput.trim())) {
				codeResponse.setSuccess("true");
				codeResponse.setMessage("Test case passed!");
			} else {
				codeResponse.setSuccess("false");
				codeResponse.setMessage(
						"Test case failed. Expected: " + expectedOutput + ", Actual: " + codeResponse.getOutput());
			}

			if ("Error: Execution timed out after 10 seconds".equals(codeResponse.getOutput())) {
				tempFile.delete();
				return codeResponse;
			}

			tempFile.delete();
			return codeResponse;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			CodeResponse codeResponse = new CodeResponse();
			codeResponse.setOutput("Error: " + e.getMessage());
			codeResponse.setMessage("Test case failed. Error: " + e.getMessage());
			return codeResponse;
		}
	}

	private CodeResponse executeProcess(ProcessBuilder processBuilder, File tempFile) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		CodeResponse codeResponse = new CodeResponse();

		long startTime = System.currentTimeMillis();

		try {
			Future<Void> future = CompletableFuture.runAsync(() -> {
				try {
					executeAndCaptureOutput(processBuilder, codeResponse, tempFile);
				} catch (Exception e) {
					handleTimeoutError(codeResponse);
				}
			}, executorService);

			future.get(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			handleExecutionError(codeResponse);
		} finally {
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			codeResponse.setProcessingTime(elapsedTime);

			executorService.shutdownNow();
		}

		return codeResponse;
	}

	private void executeAndCaptureOutput(ProcessBuilder processBuilder, CodeResponse codeResponse, File tempFile) {
		try {
			processBuilder.redirectErrorStream(true);
			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder output = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line);
			}

			codeResponse.setOutput(output.toString());
		} catch (IOException e) {
			logger.error("Error:" + e.getMessage(), e);
		}
	}

	private void handleTimeoutError(CodeResponse codeResponse) {
		logger.warn("Execution timed out after 10 seconds");
		codeResponse.setOutput("Error: Execution timed out after 10 seconds");
	}

	private void handleExecutionError(CodeResponse codeResponse) {
		logger.warn("Error executing process");
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
			List<TestCaseEntity> testCases, List<String> expectedOutputs, int numberOfTestCasesToExecute) {
		List<String> inputs = testCases.stream().map(TestCaseEntity::getInputs).collect(Collectors.toList());

		for (int i = 0; i < numberOfTestCasesToExecute; i++) {

			CodeResponse codeResponse = codeResponses.get(i);
			if (codeResponse.getOutput() == "Error: Execution timed out after 10 seconds") {
				break;
			}
			String input = inputs.get(i);
			String expectedOutput = expectedOutputs.get(i);

			createCodeExecutionResult(codeRequest, codeResponse, input, expectedOutput);

			try {
			} catch (DataIntegrityViolationException e) {
				logger.error("Error:" + e.getMessage(), e);
				handleSaveResultError(e);
			}

		}
	}

	private CodeExecutionResult createCodeExecutionResult(CodeRequest codeRequest, CodeResponse codeResponse,
			String input, String expectedOutput) {
		try {
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
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return null;

		}
	}

	private void handleSaveResultError(DataIntegrityViolationException e) {
		logger.error("Error:" + e.getMessage(), e);
	}
}
