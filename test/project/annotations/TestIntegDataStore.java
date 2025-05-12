package project.annotations;

import main.grpc.NumberChain;
import main.java.DataStore;
import main.java.DataStoreImpl;
import main.java.DigitChains;
import main.java.InputConfig;
import main.java.OutputConfig;
import main.java.OutputResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
public class TestIntegDataStore {

    @Test
    public void testDataStoreAppendResult() throws IOException {
        DataStore dataStore = new DataStoreImpl();
        List<Integer> inputData = List.of(44, 32, 15);
        
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

        List<Integer> outputData = new ArrayList<>();
        
        OutputConfig outputConfig = new OutputConfig() {
            @Override
            public String getFilePath() {
                return null; // or actual file path if needed
            }
            @Override
            public void writeResults(Iterable<NumberChain> chains, String delimiter) throws IOException {
                for (NumberChain chain : chains) {
                    outputData.addAll(chain.getNumbersList());
                }
            }
        };

        List<Iterable<Integer>> chains = new ArrayList<>();
        for (Integer number : inputData) {
            chains.add(List.of(number));
        }
        DigitChains digitChains = new DigitChains(chains);

        OutputResult outputResult = dataStore.appendResult(outputConfig, digitChains, ";");
        
        assertEquals(OutputResult.ShowResultStatus.SUCCESS, outputResult.getStatus());
        assertFalse(outputData.isEmpty());
        assertEquals(3, outputData.size());
    }
}