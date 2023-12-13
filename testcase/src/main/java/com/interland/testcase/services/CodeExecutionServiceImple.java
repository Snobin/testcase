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
public class CodeExecutionServiceImple implements CodeExecutionService{

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
	        // Write code to a temporary file
	        File tempFile = File.createTempFile("abc", ".java");
	        FileWriter fileWriter = new FileWriter(tempFile);
	        fileWriter.write(code);
	        fileWriter.close();

	        // Set the correct path for the Docker volume
	        String dockerVolumePath = "/tmp";

	        // Extract the class name from the code
	        String className = getClassName(code);

	        ProcessBuilder processBuilder = new ProcessBuilder(
	                "docker", "run", "--rm", "-i",
	                "-v", tempFile.getParent() + ":" + dockerVolumePath,
	                "tomcat:latest",
	                "bash", "-c",
	                "javac " + dockerVolumePath + "/" + tempFile.getName() + " && cd " + dockerVolumePath + " && java " + className
	        );

	        processBuilder.redirectErrorStream(true);
	        Process process = processBuilder.start();

	        // Wait for the process to finish
	        int exitCode = process.waitFor();

	        // Read the output of the process
	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        StringBuilder output = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            output.append(line).append("\n");
	        }

	        // Clean up temporary file
	        tempFile.delete();

	        if (exitCode == 0) {
	            return output.toString();
	        } else {
	            return "Compilation or execution error:\n" + output.toString();
	        }
	    } catch (Exception e) {
	        return "Error: " + e.getMessage();
	    }
	}

    private String getClassName(String code) {
        // Extract the class name from the Java code
        // This is a simplified approach and may not cover all cases
        String className = code.replaceAll("(?s).*?\\bclass\\s+(\\w+).*", "$1");
        return className.trim();
    }


    private String executeCCode(String code) {
        try {
            // Write code to a temporary file
            File tempFile = File.createTempFile("code", ".c");
            FileWriter fileWriter = new FileWriter(tempFile);
            fileWriter.write(code);
            fileWriter.close();

            // Set the correct path for the Docker volume
            String dockerVolumePath = "/tmp";

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "docker", "run", "--rm", "-i",
                    "-v", tempFile.getParent() + ":" + dockerVolumePath,
                    "eclipse/cpp_gcc:latest",
                    "bash", "-c",
                    "gcc " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath + "/a.out && cd " + dockerVolumePath + " && ./a.out"
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Wait for the process to finish
            int exitCode = process.waitFor();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Clean up temporary file
            tempFile.delete();

            if (exitCode == 0) {
                return output.toString();
            } else {
                return "Compilation or execution error:\n" + output.toString();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private String executeCppCode(String code) {
        try {
            // Write code to a temporary file
            File tempFile = File.createTempFile("code", ".cpp");
            FileWriter fileWriter = new FileWriter(tempFile);
            fileWriter.write(code);
            fileWriter.close();

            // Set the correct path for the Docker volume
            String dockerVolumePath = "/tmp";

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "docker", "run", "--rm", "-i",
                    "-v", tempFile.getParent() + ":" + dockerVolumePath,
                    "eclipse/cpp_gcc:latest",
                    "bash", "-c",
                    "g++ " + dockerVolumePath + "/" + tempFile.getName() + " -o " + dockerVolumePath + "/a.out && cd " + dockerVolumePath + " && ./a.out"
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Wait for the process to finish
            int exitCode = process.waitFor();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Clean up temporary file
            tempFile.delete();

            if (exitCode == 0) {
                return output.toString();
            } else {
            	
                return "Compilation or execution error:\n" + output.toString();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }


    private String executePythonCode(String code) {
    	 StringWriter outputWriter = new StringWriter();

         try (// Create a PythonInterpreter
 		PythonInterpreter interpreter = new PythonInterpreter()) {
 			// Redirect the standard output to the StringWriter
 			interpreter.setOut(outputWriter);

 			// Execute the Python code
 			interpreter.exec(code);
 		}

         // Retrieve the output from the StringWriter
         String output = outputWriter.toString();

         // Print the output (for testing purposes)
         System.out.println("Python Output: " + output);

         // Now you can use the 'output' variable for further purposes
         return output;
    }

    private String executeCodeInDocker(String code, String compiler, String outputFileName, String input) {
        try {
            // Write code to a temporary file
            File tempFile = File.createTempFile("code", ".cpp");
            FileWriter fileWriter = new FileWriter(tempFile);
            fileWriter.write(code);
            fileWriter.close();

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "docker", "run", "--rm", "-i",
                    "-v", "/tmp:/tmp",
                    compiler,
                    "bash", "-c",
                    "g++ " + tempFile.getName() + " -o " + outputFileName + " && ./" + outputFileName
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Wait for the process to finish
            int exitCode = process.waitFor();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Clean up temporary file
            tempFile.delete();

            if (exitCode == 0) {
                return output.toString();
            } else {
                return "Compilation or execution error:\n" + output.toString();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
