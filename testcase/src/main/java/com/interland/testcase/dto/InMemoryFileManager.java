package com.interland.testcase.dto;

//InMemoryFileManager.java
import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class InMemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {
 private ByteArrayOutputStream output;

 public InMemoryFileManager(JavaFileManager fileManager) {
     super(fileManager);
     output = new ByteArrayOutputStream();
 }

 public String getOutput() {
     return output.toString();
 }

 @Override
 public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
     return new SimpleJavaFileObject(URI.create("string:///compiled/" + className + kind.extension), kind) {
         @Override
         public OutputStream openOutputStream() throws IOException {
             return output;
         }
     };
 }
}
