package com.interland.testcase.dto;

import java.nio.charset.StandardCharsets;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.*;

public class InMemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {
	private ByteArrayOutputStream output;

    public InMemoryFileManager(JavaFileManager fileManager) {
        super(fileManager);
        output = new ByteArrayOutputStream();
    }

    public String getOutput() {
    	// Check if the data is binary (contains non-printable characters)
        boolean isBinary = !StandardCharsets.UTF_8.newEncoder().canEncode(output.toString());

        if (isBinary) {
            // If binary, encode as a hexadecimal string or Base64 (choose one)
            return bytesToHexString(output.toByteArray());
            // OR
            // return Base64.getEncoder().encodeToString(output.toByteArray());
        } else {
            // If text, use UTF-8 decoding
            return new String(output.toByteArray(), StandardCharsets.UTF_8);
        }
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
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
