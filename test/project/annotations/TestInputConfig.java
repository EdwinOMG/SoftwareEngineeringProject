package project.annotations;

import java.util.Arrays;

import main.java.InputConfig;

public class TestInputConfig implements InputConfig {

    private Iterable<Integer> inputData;

    // Constructor to initialize inputData
    public TestInputConfig(Iterable<Integer> inputData) {
        this.inputData = inputData;
    }

    // Implementing the method from InputConfig
    @Override
    public Iterable<Integer> getInput() {
        return inputData;
    }

    // Implementing the getFilePath() method as required by the InputConfig interface
    @Override
    public String getFilePath() {
        return "mock/input/file/path";  // Provide a mock file path for testing
    }
}
