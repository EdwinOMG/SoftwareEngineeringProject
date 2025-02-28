package project.annotations;
import java.util.Arrays;

public class TestInputConfig implements InputConfig {
    public Iterable<Integer> getInput() {
        return Arrays.asList(1, 2, 3);  // Example mock input data
    }

    public String getFilePath() {
        return "mock/input/file/path";  // Mock file path for testing
    }
}
