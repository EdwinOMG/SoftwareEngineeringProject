package project.annotations;

import main.java.DataStore;
import main.java.DataStoreImpl;
import main.java.DigitChains;
import main.java.InputConfig;
import main.java.OutputConfig;
import main.java.OutputResult;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestIntegDataStore {

    @Test
    public void testDataStoreAppendResult() {
        // Create an instance of DataStoreImpl
        DataStore dataStore = new DataStoreImpl();

        // Simulate input data (e.g., a list of numbers to process)
        List<Integer> inputData = List.of(44, 32, 15);

        // Create an InputConfig implementation inline
        InputConfig inputConfig = () -> inputData;

        // Create a list to store the output data
        List<Integer> outputData = new ArrayList<>();

        // Create an OutputConfig implementation inline using a lambda
        OutputConfig outputConfig = result -> outputData.add(result);

        // Define the delimiter
        char delimiter = ';'; // Example delimiter

        // Simulate creating DigitChains
        List<Iterable<Integer>> chains = new ArrayList<>();
        for (Integer number : inputData) {
            List<Integer> chain = new ArrayList<>();
            chain.add(number); // Example: Add the number to the chain
            chains.add(chain);
        }
        DigitChains digitChains = new DigitChains(chains);

        // Append the results to the output config
        OutputResult outputResult = dataStore.appendResult(outputConfig, digitChains, delimiter);

        // Verify the result
        assertEquals("OutputResult status should be SUCCESS", OutputResult.ShowResultStatus.SUCCESS, outputResult.getStatus());
        assertFalse("Output shouldn't be empty", outputData.isEmpty());
    }
}