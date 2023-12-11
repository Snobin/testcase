// CodeExecutionController.java
package com.interland.testcase.controller;

import java.io.IOException;
import java.io.StringWriter;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeController extends PythonInterpreter {

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
}
