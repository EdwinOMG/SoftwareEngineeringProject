package main.java;

import java.util.List;

public class PrototypeComputeEngine {

    public void prototype(ComputeEngine computeEngine) {
        // Simulate input data (e.g., a list of numbers to process)
        List<Integer> inputNumbers = List.of(44, 32, 15); // Example input

        // Execute the computation using the provided ComputeEngine
        DigitChains chains = computeEngine.compute(inputNumbers);

        // Print the computed chains (for debugging or demonstration purposes)
        
    }
}