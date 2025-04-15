package project.annotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.ComputeEngineImpl;
import main.java.DigitChains;

public class TestComputeEngine {
    private ComputeEngineImpl computeEngine;

    @BeforeEach 
    public void setUp() {
        computeEngine = new ComputeEngineImpl();
    }

    @Test
    public void testComputeEngineExists() {
        assertNotNull(computeEngine); // Make sure it exists
    }

    @Test
    public void testComputeMethodReturnsDigitChains() {
        // Simulate input data
        List<Integer> input = List.of(5, 10, 15);

        // Compute the digit chains
        DigitChains result = computeEngine.compute(input);

        // Verify the result
        assertNotNull(result, "Compute method should return a non-null result");
    }
}