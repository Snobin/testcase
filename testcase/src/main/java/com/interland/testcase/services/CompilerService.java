package com.interland.testcase.services;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface CompilerService {
    String compileAndRun(String code, String language) throws IOException, InterruptedException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException;
}