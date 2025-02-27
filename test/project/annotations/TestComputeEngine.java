package project.annotations;


import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import main.java.ComputeEngine;
import main.java.ComputeEngineImpl;

public class TestComputeEngine {

	private ComputeEngineImpl computeEngine;
	
	@Before
	public void setUp() {
		computeEngine = new ComputeEngineImpl();
	}
	@Test
	public void testComputeEngineExists() {
		assertNotNull(computeEngine); // make sure it exist
	}
	
	@Test 
	public void testComputeMethodReturnsListOfN() {
		int input = 5;
		
		Iterable<Integer> result = computeEngine.compute(input);
		assertNotNull("Compute method should be a list of the input");
		
	}
	
}
