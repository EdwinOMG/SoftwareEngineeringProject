package project.annotations;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.grpc.NumberChain;
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

	@Override
	public String getFilePath() {
		return null;
	}

	@Override
	public void writeResults(Iterable<NumberChain> chains, String delimiter) throws IOException {
		// TODO Auto-generated method stub
		
	}
}

