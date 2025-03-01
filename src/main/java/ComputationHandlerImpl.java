package main.java;

import java.util.ArrayList;

public class ComputationHandlerImpl {
    private final DataStore dataStore;
    private final ComputeEngine computeEngine;

    public ComputationHandlerImpl(DataStore dataStore, ComputeEngine computeEngine) {
        this.dataStore = dataStore;
        this.computeEngine = computeEngine;
    }

    public ComputeEngineResult compute(InputConfig inputConfig, OutputConfig outputConfig) {
        // Step b: Request data from the data storage component
        Iterable<Integer> numbers = dataStore.read(inputConfig);

        // Step c: Pass the integers to the compute component
        Iterable<Integer> result = computeNumbers(numbers);

        // Step d: Write the results to the output
        OutputResult outputResult = dataStore.appendResult(outputConfig, result, ';');
        if (outputResult.getStatus() == OutputResult.ShowResultStatus.SUCCESS) {
            return ComputeEngineResult.SUCCESS;
        } else {
            return new ComputeEngineResult() {
                @Override
                public ComputeEngineResultStatus getStatus() {
                    return ComputeEngineResultStatus.FAIL;
                }

                @Override
                public String getFailureMessage() {
                    return "Failed to store results.";
                }
            };
        }
    }

    private Iterable<Integer> computeNumbers(Iterable<Integer> numbers) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int num : numbers) {
            Iterable<Integer> chain = computeEngine.compute(num);
            results.add(getLastNumber(chain));  // Collect the last number of each chain (1 or 89)
        }
        return results;
    }

    private int getLastNumber(Iterable<Integer> chain) {
        for (int num : chain) {
            // Return the last number in the chain (either 1 or 89)
        }
        return 0;  // Default return if something goes wrong
    }
}
