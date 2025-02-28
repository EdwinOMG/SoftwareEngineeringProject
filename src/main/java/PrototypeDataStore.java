package main.java;

import main.java.project.annotations.ProcessAPIPrototype;

import java.util.ArrayList;
import java.util.List;

public class PrototypeDataStore {

    @ProcessAPIPrototype
    public void prototype(DataStore dataStore) {
        List<Integer> inputNumbers = List.of(44, 32, 15);
        InputConfig inputConfig = () -> inputNumbers;
        List<Integer> outputData = new ArrayList<>();
        OutputConfig outputConfig = result -> outputData.add(result);
        char delimiter = ';';

        Iterable<Integer> readData = dataStore.read(inputConfig);
        DigitChains chains = new DigitChains(List.of(readData)); // Wrap the read data in DigitChains

        OutputResult outputResult = dataStore.appendResult(outputConfig, chains, delimiter);

        if (outputResult.getStatus() != OutputResult.ShowResultStatus.SUCCESS) {
            System.out.println("FAILED");
        } else {
            System.out.println("SUCCESS");
            System.out.println("Output Data:");
            for (Integer number : outputData) {
                System.out.print(number + " ");
            }
            System.out.println();
        }
    }
}