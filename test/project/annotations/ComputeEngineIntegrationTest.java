package project.annotations;

import org.junit.Before;
import org.junit.Test;
import main.java.ComputeEngine;
import main.java.ComputeEngineImpl;
import main.java.ComputeEngineResult;
import main.java.DataStoreImpl;

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
        List<Integer> inputData = List.of(50);
        TestInputConfig inputConfig = new TestInputConfig(inputData);
        TestOutputConfig outputConfig = new TestOutputConfig();

        dataStore.appendResult(outputConfig, inputData);

        Iterable<Integer> result = computeEngine.compute(inputData.get(0));
        assertNotNull("Result should not be null", result);

        List<Integer> outputData = (List<Integer>) outputConfig.getOutput();
        assertNotNull("Output shouldn't be null", outputData);
        assertFalse("Output shouldn't be empty", outputData.isEmpty());
    }
}