package project.annotations;

import main.java.ComputeEngine;
import main.java.ComputeEngineImpl;
import main.java.DataStoreImpl;
import main.java.DigitChains;
import main.java.InputConfig;
import main.java.OutputConfig;
import main.java.OutputResult;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

public class ComputeEngineIntegrationTest {
    private ComputeEngine computeEngine;
    private DataStoreImpl dataStore;

    @Before
    public void setUp() {
        computeEngine = new ComputeEngineImpl();
        dataStore = new DataStoreImpl();
    }

    @Test
    public void testComputeEngineIntegration() {
        // Simulate input data
        List<Integer> inputData = List.of(44, 32, 15);

        // Create an InputConfig implementation inline
        InputConfig inputConfig = () -> inputData;

        // Create an OutputConfig implementation inline
        List<Integer> outputData = new ArrayList<>();
        OutputConfig outputConfig = result -> outputData.add(result);

        // Compute the digit chains using the ComputeEngine
        DigitChains chains = computeEngine.compute(inputData);

        // Append the results to the output config
        OutputResult outputResult = dataStore.appendResult(outputConfig, chains, ';');

        // Verify the result
        assertNotNull("Result should not be null", outputResult);
        assertFalse("Output shouldn't be empty", outputData.isEmpty());
    }
}