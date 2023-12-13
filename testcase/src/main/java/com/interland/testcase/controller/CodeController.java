// CodeExecutionController.java
package com.interland.testcase.controller;

import java.io.IOException;
import java.io.StringWriter;

import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.interland.testcase.dto.CodeRequest;
import com.interland.testcase.services.CodeExecutionServiceImple;

@RestController
public class CodeController extends PythonInterpreter {
	 private final Logger log = LoggerFactory.getLogger(CodeController.class);

	 

	 
	@PostMapping("/execute/python")

	public String executePythonCode(@RequestBody String code) throws IOException, InterruptedException {
        // Create a StringWriter to capture the output
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

	    @Autowired
	    private CodeExecutionServiceImple codeExecutionService;

	    @PostMapping("/execute")
	    public ResponseEntity<String> executeCode(@RequestBody CodeRequest codeRequest) {
	    	System.out.println("REACHED");
	        String output = codeExecutionService.executeCode(codeRequest);
	        return ResponseEntity.ok(output);
	    }
	
}