

import java.util.List;

import main.java.InputConfig;

public class TestInputConfig implements InputConfig {
    private final List<Integer> inputValues;

    public TestInputConfig(List<Integer> inputValues) {
        this.inputValues = inputValues;
    }

    @Override
    // need to create a getIntput() method in InputConfig interface
    public List<Integer> getInput() {
        return inputValues;
    }
}
