package project.annotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import main.java.ComputeEngine;
import main.java.ComputeEngineImpl;
import main.java.ComputeEngineRequest;
import main.java.ComputeEngineResult;
import main.java.DataStoreImpl;
import main.java.InputConfig;
import main.java.OutputConfig;

public class ComputeEngineIntegrationTest {
    private ComputeEngine computeEngine;
    private DataStoreImpl dataStore;

    @Before
    public void setUp() {
        computeEngine = new ComputeEngineImpl();  // Ensure this is implemented!
        dataStore = new DataStoreImpl();
    }

    @Test
    public void testComputeEngineIntegration() {
    	int input = 40;
    	InputConfig inputConfig = new InputConfig();
        OutputConfig outputConfig = new OutputConfig();
        // simulate storing input data
        dataStore.appendResult(outputConfig, List.of(input));
        
        // Compute
        ComputeEngineResult result = (ComputeEngineResult) computeEngine.compute(input);
        assertNotNull("Result should not be null", result);

        // Read output
        Iterable<Integer> outputData = dataStore.read(inputConfig);
        assertNotNull("Output shouldn't be null", outputData);
    }
}
