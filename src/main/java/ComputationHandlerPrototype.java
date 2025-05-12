package main.java;

import main.grpc.NumberChain;
import main.java.project.annotations.NetworkAPIPrototype;

import java.util.ArrayList;
import java.util.List;

public class ComputationHandlerPrototype {

    @NetworkAPIPrototype
    public void prototype(ComputationHandler apiToCall) {
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

        ComputeEngineRequest request = new ComputeEngineRequest(
            inputConfig,
            outputConfig,
            ";" // delimiter
        );

        ComputeEngineResult result = apiToCall.compute(request);

        if (result.getStatus().isSuccess()) {
            System.out.println("Computation succeeded!");
            System.out.println("Output Chains:");
            outputChains.forEach(chain -> {
                System.out.print("Chain: ");
                chain.getNumbersList().forEach(num -> System.out.print(num + " "));
                System.out.println();
            });
        } else {
            System.out.println("Computation failed.");
        }
    }
}