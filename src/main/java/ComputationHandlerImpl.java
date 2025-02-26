package main.java;

import java.util.List;

public class ComputationHandlerImpl implements ComputationHandler {
    
    private final ComputeEngine computeEngine;
    private final DataStore dataStore;

    public ComputationHandlerImpl(ComputeEngine computeEngine, DataStore dataStore) {
        this.computeEngine = computeEngine;
        this.dataStore = dataStore;
    }

    @Override
    public ComputeEngineResult compute(ComputeEngineRequest request) {
        // Step 1: Read integers from the data store based on the user's input configuration
        Iterable<Integer> data = dataStore.read(request.getInput());
        
        if (data == null) {
            return ComputeEngineResult.FAIL;
        }

        // Step 2: Pass the integers to the compute engine for processing
        Iterable<Integer> computedResults = computeEngine.compute(data);

        // Step 3: Write the results back to the data store
        OutputResult result = dataStore.appendResult(request.getOutput(), computedResults);
        
        return result.getStatus() == OutputResult.ShowResultStatus.SUCCESS 
            ? ComputeEngineResult.SUCCESS 
            : ComputeEngineResult.FAIL;
    }
}
