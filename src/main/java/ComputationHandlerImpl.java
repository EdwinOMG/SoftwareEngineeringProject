package main.java;

<<<<<<< Updated upstream
import java.util.List;

public class ComputationHandlerImpl implements ComputationHandler {
    
=======
import java.util.ArrayList;

public class ComputationHandlerImpl implements ComputationHandler {

>>>>>>> Stashed changes
    private final ComputeEngine computeEngine;
    private final DataStore dataStore;

    public ComputationHandlerImpl(ComputeEngine computeEngine, DataStore dataStore) {
        this.computeEngine = computeEngine;
        this.dataStore = dataStore;
    }

    @Override
    public ComputeEngineResult compute(ComputeEngineRequest request) {
<<<<<<< Updated upstream
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
=======
        // Step 1: Read input numbers from the specified location
        Iterable<Integer> numbers = dataStore.read(request.getInputConfig());

        // Step 1, Part 2: Check for null data and return failure if necessary
        if (numbers == null) {
            return ComputeEngineResult.FAIL;
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
        return new ComputeEngineResult(outputResult.getStatus());
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
>>>>>>> Stashed changes
    }
}
