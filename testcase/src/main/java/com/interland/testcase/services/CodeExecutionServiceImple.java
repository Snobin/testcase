package com.interland.testcase.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import com.interland.testcase.dto.CodeRequest;

@Service
public class CodeExecutionServiceImple implements CodeExecutionService {

	@Override
	public String executeCode(CodeRequest codeRequest) {
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
			return "Unsupported language";
		}
	}

	private String executeJavaCode(String code) {
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
			return "Error: " + e.getMessage();
		}
	}

	private String executeCCode(String code) {
		try {
			File tempFile = createTempFile("code", ".c", code);
			String dockerVolumePath = "/tmp";

			ProcessBuilder processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
					tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "bash", "-c",
					"gcc " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath + "/a.out && cd "
							+ dockerVolumePath + " && ./a.out");

			return executeProcess(processBuilder, tempFile);
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}

	private String executeCppCode(String code) {
		try {
			File tempFile = createTempFile("code", ".cpp", code);
			String dockerVolumePath = "/tmp";

			ProcessBuilder processBuilder = new ProcessBuilder("docker", "run", "--rm", "-i", "-v",
					tempFile.getParent() + ":" + dockerVolumePath, "eclipse/cpp_gcc:latest", "bash", "-c",
					"g++ " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath + "/a.out && cd "
							+ dockerVolumePath + " && ./a.out");

			return executeProcess(processBuilder, tempFile);
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}

	private String executePythonCode(String code) {
		StringWriter outputWriter = new StringWriter();

		try (PythonInterpreter interpreter = new PythonInterpreter()) {
			interpreter.setOut(outputWriter);
			interpreter.exec(code);
		}

		String output = outputWriter.toString();
		System.out.println("Python Output: " + output);
		return output;
	}

	private String executeProcess(ProcessBuilder processBuilder, File tempFile) {
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

			return (exitCode == 0) ? output.toString() : "Compilation or execution error:\n" + output.toString();
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
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
