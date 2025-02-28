import java.util.Arrays;

import main.java.OutputConfig;

public class TestOutputConfig implements OutputConfig {
    @Override
    public void writeOutput(Integer output) {
        System.out.println("Writing output: " + output);  // Example mock output writing
    }

    @Override
    public Iterable<Integer> getOutput() {
        return Arrays.asList(1, 2, 3);  // Example mock output data
    }

    @Override
    public String getFilePath() {
        return "mock/output/file/path";  // Mock file path for testing
    }
}
