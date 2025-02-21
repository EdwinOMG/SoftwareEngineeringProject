package project.annotations;


import java.util.ArrayList;
import java.util.List;

import main.java.OutputConfig;

public class TestOutputConfig implements OutputConfig {
    private final List<Integer> outputValues = new ArrayList<>();

    
    //create writeOutput method in OutputConfig interface
    public void writeOutput(Integer output) {
        outputValues.add(output);
    }

    public Iterable<Integer> getOutput() {
        return outputValues;
    }
}

