package project.annotations;
import java.util.ArrayList;
import java.util.List;

import main.java.DataStore;
import main.java.InputConfig;
import main.java.OutputConfig;
import main.java.OutputResult;

public class TestIntegDataStore implements DataStore {

    @Override
    public Iterable<Integer> read(InputConfig inputConfig) {
        return inputConfig.getInput(); // Get the input from the TestInputConfig
    }

    @Override
    public OutputResult appendResult(OutputConfig outputConfig, Iterable<Integer> readData , char delimiter) {
        List<Integer> processedResults = new ArrayList<>();

        // Process the integers and write to outputConfig using writeOutput(Integer)
        for (Integer number : readData) {
            Integer result = number + 1; // Simulating processing (e.g., incrementing by 1)
            processedResults.add(result);
            outputConfig.writeOutput(result); // Write Integer output to TestOutputConfig
        }

        // Return status directly without creating a separate class
        return new OutputResult() {
            @Override
            public ShowResultStatus getStatus() {
                return ShowResultStatus.SUCCESS; // Assume success for this test case
            }
        };
    }
}
