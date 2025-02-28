package main.java;

import java.util.ArrayList;
import java.util.List;

public class DataStoreImpl implements DataStore {

    @Override
    public Iterable<Integer> read(InputConfig inputConfig) {
        return inputConfig.getInput();
    }

    @Override
    public OutputResult appendResult(OutputConfig outputConfig, DigitChains chains, char delimiter) {
        for (Iterable<Integer> chain : chains) {
            for (Integer number : chain) {
                outputConfig.writeOutput(number); // Write each number to the output config
                outputConfig.writeOutput((int) delimiter); // Write the delimiter as an integer
            }
        }

        return new OutputResult() {
            @Override
            public ShowResultStatus getStatus() {
                return ShowResultStatus.SUCCESS;
            }
        };
    }
}