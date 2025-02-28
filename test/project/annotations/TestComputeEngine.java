package project.annotations;

import main.java.ComputeEngineImpl;
import main.java.DigitChains;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class TestComputeEngine {
    private ComputeEngineImpl computeEngine;

    @Before
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
        assertNotNull("Compute method should return a non-null result", result);
    }
}