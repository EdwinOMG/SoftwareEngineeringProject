package project.annotations;

import main.grpc.NumberChain;
import main.java.ComputeEngine;
import main.java.ComputeEngineImpl;
import main.java.DataStore;
import main.java.DataStoreImpl;
import main.java.DigitChains;
import main.java.InputConfig;
import main.java.OutputConfig;
import main.java.OutputResult;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestComputeEngineIntegration {
    private ComputeEngine computeEngine;
    private DataStore dataStore;

    @Before
    public void setUp() {
        computeEngine = new ComputeEngineImpl();
        dataStore = new DataStoreImpl();
    }

    @Test
    public void testComputeEngineIntegration() throws IOException {
        List<Integer> inputData = List.of(44, 32, 15);
        List<Integer> outputData = new ArrayList<>();
        
        InputConfig inputConfig = new InputConfig() {
            @Override
            public List<Integer> getInput() {
                return inputData;
            }
            @Override
            public String getFilePath() {
                return null;
            }
        };

        OutputConfig outputConfig = new OutputConfig() {
            @Override
            public String getFilePath() {
                return null;
            }
            @Override
            public void writeResults(Iterable<NumberChain> chains, String delimiter) throws IOException {
                for (NumberChain chain : chains) {
                    outputData.addAll(chain.getNumbersList());
                }
            }
        };

        DigitChains chains = computeEngine.compute(inputConfig.getInput());
        dataStore.appendResult(outputConfig, chains, ";");
        
        assertFalse(outputData.isEmpty());
        assertEquals(3, outputData.size());
    }
}