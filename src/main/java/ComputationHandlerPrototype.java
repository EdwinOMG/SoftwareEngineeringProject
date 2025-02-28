package main.java;

import main.java.project.annotations.NetworkAPIPrototype;

import java.util.ArrayList;
import java.util.List;

public class ComputationHandlerPrototype {

    @NetworkAPIPrototype
    public void prototype(ComputationHandler apiToCall) {
        List<Integer> inputNumbers = List.of(44, 32, 15);
        InputConfig inputConfig = () -> inputNumbers;
        List<Integer> outputData = new ArrayList<>();
        OutputConfig outputConfig = result -> outputData.add(result);
        ComputeEngineRequest request = new ComputeEngineRequest(inputConfig, outputConfig);
        ComputeEngineResult result = apiToCall.compute(request);

        if (result.getStatus().isSuccess()) {
            System.out.println("Computation succeeded!");
            System.out.println("Output Data:");
            for (Integer number : outputData) {
                System.out.print(number + " ");
            }
            System.out.println();
        } else {
            System.out.println("Computation failed.");
        }
    }
}