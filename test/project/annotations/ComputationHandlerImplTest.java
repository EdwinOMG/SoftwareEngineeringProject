package test.project.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;


class ComputationHandlerImplTest {

    @Test
    void testCompute_InvalidParameters() throws Exception{
        // Arrange
        ComputeEngine mockComputeEngine = mock(ComputeEngine.class);
        DataStore mockDataStore = mock(DataStore.class);
        ComputationHandlerImpl handler = new ComputationHandlerImpl(mockComputeEngine, mockDataStore);

        // Test cases for invalid parameters
        ComputeEngineRequest nullRequest = null;
        ComputeEngineRequest nullInput = new ComputeEngineRequest(null, "output", ",");
        ComputeEngineRequest nullOutput = new ComputeEngineRequest("input", null, ",");
        ComputeEngineRequest nullInputAndOutput = new ComputeEngineRequest(null, null, ",");

        // Act & Assert
        assertEquals(ComputeEngineResult.FAIL, handler.compute(nullRequest), "Expected FAIL for null request");
        assertEquals(ComputeEngineResult.FAIL, handler.compute(nullInput), "Expected FAIL for null input");
        assertEquals(ComputeEngineResult.FAIL, handler.compute(nullOutput), "Expected FAIL for null output");
        assertEquals(ComputeEngineResult.FAIL, handler.compute(nullInputAndOutput), "Expected FAIL for null input and output");
    }
}