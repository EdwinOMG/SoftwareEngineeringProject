package main.java;

import java.util.ArrayList;

public class ComputationHandlerImpl implements ComputationHandler {

    private final ComputeEngine computeEngine;
    private final DataStore dataStore;

    public ComputationHandlerImpl(ComputeEngine computeEngine, DataStore dataStore) {
        this.computeEngine = computeEngine;
        this.dataStore = dataStore;
    }

    @Override
    public ComputeEngineResult compute(ComputeEngineRequest request) {
        // Step 1: Read input numbers from the specified location
        Iterable<Integer> numbers = dataStore.read(request.getInputConfig());

        // Step 1, Part 2: Check for null data and return failure if necessary
        if (numbers == null) {
            return new ComputeEngineResultImpl(ShowResultStatus.FAILURE);
        }

        // Step 2: Compute the number chain for each number
        Iterable<String> results = new ArrayList<>();
        for (int num : numbers) {
            Iterable<Integer> chain = computeEngine.computeNumberChain(num);
            ((ArrayList<String>) results).add(formatChain(num, chain));  // Output full chain
        }

        // Step 3: Write results to the specified output
        OutputResult outputResult = dataStore.appendResult(request.getOutputConfig(), results, '\n');

        // Step 4: Return the result status
        return new ComputeEngineResultImpl(outputResult.getStatus());
    }

    // Helper method to format the number chain as a string
    private String formatChain(int number, Iterable<Integer> chain) {
        StringBuilder sb = new StringBuilder();
        sb.append(number).append(": ");  // Prefix each output with the input number
        boolean first = true;
        for (int num : chain) {
            if (!first) sb.append(" -> ");
            sb.append(num);
            first = false;
        }
        return sb.toString();
    }
}

	
	private final ComputeEngine computeEngine;
	private final DataStore dataStore;
	
	public ComputationHandlerImpl(ComputeEngine computeEngine, DataStore dataStore) {
		this.computeEngine = computeEngine;
		this.dataStore = dataStore;
	}
	
	@Override
	public ComputeEngineResult compute(ComputeEngineRequest request) {
		
		return null;
	}
	
}
