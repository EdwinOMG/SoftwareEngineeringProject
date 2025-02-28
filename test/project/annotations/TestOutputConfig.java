package project.annotations;

import java.util.Arrays;

import main.java.OutputConfig;

public class TestOutputConfig implements OutputConfig {

    // Implementing the method from OutputConfig
    @Override
    public void writeOutput(Integer output) {
        System.out.println("Writing output: " + output);  // Just a mock for testing
    }

    // Implementing the getOutput() method from OutputConfig
    @Override
    public Iterable<Integer> getOutput() {
        return Arrays.asList(1, 2, 3);  // Example mock output
    }

    // Implementing the getFilePath() method as required by the OutputConfig interface
    @Override
    public String getFilePath() {
        return "mock/output/file/path";  // Provide a mock file path for testing
    }
}
