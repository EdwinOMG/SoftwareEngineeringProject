package main.java;

import java.util.ArrayList;
import java.util.List;

public class DataStoreImpl implements DataStore {

    @Override
    public Iterable<Integer> read(InputConfig inputConfig) {
    	if (inputConfig == null) {
    		throw new IllegalArgumentException("InputConfig can't be null.");
    	}
    	
    	try {
    		Iterable<Integer> inputData = inputConfig.getInput();
    		if (inputData == null) {
    			throw new IllegalStateException("InputConfig returned null");
    		}
    		return inputData;
    	} catch (Exception e) {
    		throw new RuntimeException("Failed to read data: " + e.getMessage(), e);
    	}
    }

    @Override
    public OutputResult appendResult(OutputConfig outputConfig, DigitChains chains, char delimiter) {
       if (outputConfig == null || chains == null) {
    	   throw new IllegalArgumentException("OutputConfig and DigitChains can't be null");
       }
       try {
    	for (Iterable<Integer> chain : chains) {
            for (Integer number : chain) {
                outputConfig.writeOutput(number); // Write each number to the output config
                outputConfig.writeOutput((int) delimiter); // Write the delimiter as an integer
            }
        }

        return new OutputResult() {
            @Override
            public ShowResultStatus getStatus() {
                return ShowResultStatus.SUCCESS;
            }
        };
    } catch (Exception e) {
    	throw new RuntimeException("Failed to append");
    }
}
}
//delete this later