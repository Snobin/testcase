// CodeExecutionController.java
package com.interland.testcase.controller;

import java.io.IOException;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeController {

    @PostMapping("/execute/python")

    public String executePythonCode(@RequestBody String code) throws IOException, InterruptedException {
//            String pythonCode = "print('Hello, Python!')";
            
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.exec(code);

            PyObject locals = interpreter.getLocals();
            PyObject result = locals.__finditem__(Py.newString("__builtins__"));
            PyObject pyObject = locals.__finditem__(Py.newString("__builtin__"));
            System.out.println(pyObject);
            return result.toString();
        }
    }

