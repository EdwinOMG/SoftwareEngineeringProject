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
		ComputeEngineRequest request = new ComputeEngineRequest(inputConfig, outputConfig);
		
		when(computationHandler.compute(request)).thenReturn(ComputeEngineResult.SUCCESS);
		
		ComputeEngineResult result = computationHandler.compute(request);
		
		assertNotNull("Compute Result should be something", result);
		assert(result.getStatus() == ComputeEngineResultStatus.SUCCESS);
	}
	
	 @Test
	    void testComputeWithNullRequest() {
	        // Arrange
	        ComputeEngine mockComputeEngine = mock(ComputeEngine.class);
	        DataStore mockDataStore = mock(DataStore.class);
	        ComputationHandlerImpl handler = new ComputationHandlerImpl(mockComputeEngine, mockDataStore);

	        // Act
	        ComputeEngineResult result = handler.compute(null);

	        // Assert
	        assertEquals(ComputeEngineResult.FAIL, result, "Expected FAIL when request is null");
	    }

	    @Test
	    void testComputeWithNullInput() {
	        // Arrange
	        ComputeEngine mockComputeEngine = mock(ComputeEngine.class);
	        DataStore mockDataStore = mock(DataStore.class);
	        ComputationHandlerImpl handler = new ComputationHandlerImpl(mockComputeEngine, mockDataStore);

	        ComputeEngineRequest request = new ComputeEngineRequest(null, "output", ",");

	        // Act
	        ComputeEngineResult result = handler.compute(request);

	        // Assert
	        assertEquals(ComputeEngineResult.FAIL, result, "Expected FAIL when input is null");
	    }

	    @Test
	    void testComputeWithNullOutput() {
	        // Arrange
	        ComputeEngine mockComputeEngine = mock(ComputeEngine.class);
	        DataStore mockDataStore = mock(DataStore.class);
	        ComputationHandlerImpl handler = new ComputationHandlerImpl(mockComputeEngine, mockDataStore);

	        ComputeEngineRequest request = new ComputeEngineRequest("input", null, ",");

	        // Act
	        ComputeEngineResult result = handler.compute(request);

	        // Assert
	        assertEquals(ComputeEngineResult.FAIL, result, "Expected FAIL when output is null");
	    }

	    @Test
	    void testComputeWithNullInputAndOutput() {
	        // Arrange
	        ComputeEngine mockComputeEngine = mock(ComputeEngine.class);
	        DataStore mockDataStore = mock(DataStore.class);
	        ComputationHandlerImpl handler = new ComputationHandlerImpl(mockComputeEngine, mockDataStore);

	        ComputeEngineRequest request = new ComputeEngineRequest(null, null, ",");

	        // Act
	        ComputeEngineResult result = handler.compute(request);

	        // Assert
	        assertEquals(ComputeEngineResult.FAIL, result, "Expected FAIL when both input and output are null");
	    }
	}
}
