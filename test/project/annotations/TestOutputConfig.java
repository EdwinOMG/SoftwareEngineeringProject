package project.annotations;


import java.util.ArrayList;
import java.util.List;

import main.java.OutputConfig;

public class TestOutputConfig implements OutputConfig {
    private final List<String> outputValues = new ArrayList<>();

    
    //create writeOutput method in OutputConfig interface
    public void writeOutput(String output) {
        outputValues.add(output);
    }

    public List<String> getOutput() {
        return outputValues;
    }
}

