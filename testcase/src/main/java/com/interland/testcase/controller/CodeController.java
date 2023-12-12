// CodeExecutionController.java
package com.interland.testcase.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interland.testcase.dto.CodeRequest;
import com.interland.testcase.services.CodeExecutionService;
import com.interland.testcase.services.CompilerService;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

@RestController
public class CodeController extends PythonInterpreter {
	 private final Logger log = LoggerFactory.getLogger(CodeController.class);
//	@Autowired
//	 private ApplicationProperties applicationProperties;
//	  @Autowired
//	    private UnzipUtility unzipUtility;
@Autowired
private CompilerService compilerService;
	 
//	 @PostMapping("/compile")
//	    public String compileAndRunCode(@RequestBody String code, Model model) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
//	        try {
//	            String result = compilerService.compileAndRun(code, "java"); // Change language based on user input
//	            model.addAttribute("result", result);
//	        } catch (IOException | InterruptedException e) {
//	            e.printStackTrace();
//	            model.addAttribute("result", "Error: " + e.getMessage());
//	        }
//	        return "result";
//	    }
	 
	@PostMapping("/execute/python")

	public String executePythonCode(@RequestBody String code) throws IOException, InterruptedException {
        // Create a StringWriter to capture the output
        StringWriter outputWriter = new StringWriter();

        // Create a PythonInterpreter
        PythonInterpreter interpreter = new PythonInterpreter();

        // Redirect the standard output to the StringWriter
        interpreter.setOut(outputWriter);

        // Execute the Python code
        interpreter.exec(code);

        // Retrieve the output from the StringWriter
        String output = outputWriter.toString();

        // Print the output (for testing purposes)
        System.out.println("Python Output: " + output);

        // Now you can use the 'output' variable for further purposes
        return output;
    }

    
	  public interface CLibrary extends Library {
	        CLibrary INSTANCE = Native.load(Platform.isWindows() ? "msvcrt" : "c", CLibrary.class);

	        void printf(String format, Object... args);
	    }

//	    @RequestMapping("/compile")
//	    public String showCompileForm() {
//	        return "compileForm";
//	    }

	    @PostMapping("/compile")
	    public String compileCode(@RequestParam("code") String code, Model model) {
	        String result = compileCCodeInDocker(code);
	        model.addAttribute("result", result);
	        return "compileResult";
	    }

	    private String compileCCodeInDocker(String code) {
	        try {
	            String command = "docker run --rm -i gcc:latest /bin/sh -c 'gcc -x c -o /usr/src/app/output - && /usr/src/app/output'";
	            Process process = Runtime.getRuntime().exec(command);

	            process.getOutputStream().write(code.getBytes());
	            process.getOutputStream().close();

	            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            StringBuilder output = new StringBuilder();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                output.append(line).append("\n");
	            }

	            int exitCode = process.waitFor();
	            if (exitCode == 0) {
	                return "Compilation and Execution Successful:\n" + output.toString();
	            } else {
	                return "Compilation Failed:\n" + output.toString();
	            }
	        } catch (IOException | InterruptedException e) {
	            return "Error during compilation and execution: " + e.getMessage();
	        }
	    }
	    @Autowired
	    private CodeExecutionService codeExecutionService;

	    @PostMapping("/execute")
	    public ResponseEntity<String> executeCode(@RequestBody CodeRequest codeRequest) {
	        String output = codeExecutionService.executeCode(codeRequest);
	        return ResponseEntity.ok(output);
	    }
	
}