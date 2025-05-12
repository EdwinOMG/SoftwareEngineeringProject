package main.java;

import main.grpc.NumberChain;
import main.java.project.annotations.ProcessAPIPrototype;

import java.util.ArrayList;
import java.util.List;

public class PrototypeDataStore {

    @ProcessAPIPrototype
    public void prototype(DataStore dataStore) {
        // Create input with numbers
        List<Integer> inputNumbers = List.of(44, 32, 15);
        InputConfig inputConfig = new InputConfig() {
            @Override
            public List<Integer> getInput() {
                return inputNumbers;
            }
            
            @Override
            public String getFilePath() {
                return null; // Not using file input
            }
        };

        // Create output that collects results
        List<NumberChain> outputChains = new ArrayList<>();
        OutputConfig outputConfig = new OutputConfig() {
            @Override
            public void writeResults(Iterable<NumberChain> chains, String delimiter) {
                chains.forEach(outputChains::add);
            }
            
            @Override
            public String getFilePath() {
                return null; // Not using file output
            }
        };

        String delimiter = ";";

        try {
            // Read data
            Iterable<Integer> readData = dataStore.read(inputConfig);
            
            // Convert to DigitChains (single chain containing all numbers)
            DigitChains chains = new DigitChains(List.of(
                () -> readData.iterator()
            ));

            // Append results
            OutputResult outputResult = dataStore.appendResult(outputConfig, chains, delimiter);

            if (outputResult.getStatus() == OutputResult.ShowResultStatus.SUCCESS) {
                System.out.println("SUCCESS");
                System.out.println("Output Chains:");
                outputChains.forEach(chain -> {
                    System.out.print("Chain: ");
                    chain.getNumbersList().forEach(num -> System.out.print(num + " "));
                    System.out.println();
                });
            } else {
                System.out.println("FAILED");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}