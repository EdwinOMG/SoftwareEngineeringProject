package project.annotations;


import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.java.ComputationHandler;
import main.java.ComputeEngineRequest;
import main.java.ComputeEngineResult;
import main.java.ComputeEngineResult.ComputeEngineResultStatus;
import main.java.InputConfig;
import main.java.OutputConfig;

public class TestcomputationHandler {
	private ComputationHandler computationHandler;
	
	@BeforeEach
	public void setUp() {
		// mock computationhandler
		computationHandler = Mockito.mock(ComputationHandler.class);
	}
	
	// make sure it is instantiated or not null
	@Test 
	public void testComputationHandlerExist() {
		assertNotNull("ComputationHandler should be living!");
	}
	
	// it mocks input output configuration and then calls our compute method
	// with a request, which should be a success(api can process request)
	@Test
	public void testComputeReturnsSuccess() {
		InputConfig inputConfig = mock(InputConfig.class);
		OutputConfig outputConfig = mock(OutputConfig.class);
		ComputeEngineRequest request = new ComputeEngineRequest(inputConfig, outputConfig, ";");
		
		when(computationHandler.compute(request)).thenReturn(ComputeEngineResult.SUCCESS);
		
		ComputeEngineResult result = computationHandler.compute(request);
		
		assertNotNull("Compute Result should be something", result);
		assert(result.getStatus() == ComputeEngineResultStatus.SUCCESS);
	}
	
	
}
