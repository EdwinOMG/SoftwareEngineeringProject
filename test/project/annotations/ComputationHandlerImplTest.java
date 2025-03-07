package test.project.annotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import src.main.java.ComputeEngineResult;
import src.main.java.ComputeEngineRequest;
import src.main.java.ComputationHandlerImpl;
import src.main.java.DataStore;
import src.main.java.ComputeEngine;
import src.main.java.InputConfig;
import src.main.java.OutputConfig;

class ComputationHandlerImplTest {

    @Test
    void testCompute_InvalidParameters() throws Exception {
        // Arrange
        ComputeEngine mockComputeEngine = mock(ComputeEngine.class);
        DataStore mockDataStore = mock(DataStore.class);
        ComputationHandlerImpl handler = new ComputationHandlerImpl(mockComputeEngine, mockDataStore);

        // Test cases for invalid parameters
        ComputeEngineRequest nullRequest = null;
        ComputeEngineRequest nullInput = new ComputeEngineRequest(null, mock(OutputConfig.class), ',');
        ComputeEngineRequest nullOutput = new ComputeEngineRequest(mock(InputConfig.class), null, ',');
        ComputeEngineRequest nullInputAndOutput = new ComputeEngineRequest(null, null, ',');

        // Act & Assert
        assertEquals(ComputeEngineResult.FAIL, handler.compute(nullRequest), "Expected FAIL for null request");
        assertEquals(ComputeEngineResult.FAIL, handler.compute(nullInput), "Expected FAIL for null input");
        assertEquals(ComputeEngineResult.FAIL, handler.compute(nullOutput), "Expected FAIL for null output");
        assertEquals(ComputeEngineResult.FAIL, handler.compute(nullInputAndOutput), "Expected FAIL for null input and output");
    }

    @Test(expected = IllegalArgumentException.class)
    void testConstructor_NullComputeEngine() {
        // Arrange
        DataStore mockDataStore = mock(DataStore.class);

        // Act & Assert
        new ComputationHandlerImpl(null, mockDataStore);
    }

    @Test(expected = IllegalArgumentException.class)
    void testConstructor_NullDataStore() {
        // Arrange
        ComputeEngine mockComputeEngine = mock(ComputeEngine.class);

        // Act & Assert
        new ComputationHandlerImpl(mockComputeEngine, null);
    }

    @Test(expected = IllegalArgumentException.class)
    void testConstructor_NullComputeEngineAndDataStore() {
        // Act & Assert
        new ComputationHandlerImpl(null, null);
    }

    @Test
    void testCompute_ValidParameters() throws Exception {
        // Arrange
        ComputeEngine mockComputeEngine = mock(ComputeEngine.class);
        DataStore mockDataStore = mock(DataStore.class);
        ComputationHandlerImpl handler = new ComputationHandlerImpl(mockComputeEngine, mockDataStore);

        InputConfig validInput = mock(InputConfig.class);
        OutputConfig validOutput = mock(OutputConfig.class);
        ComputeEngineRequest validRequest = new ComputeEngineRequest(validInput, validOutput, ',');

        // Act & Assert
        assertEquals(ComputeEngineResult.SUCCESS, handler.compute(validRequest), "Expected SUCCESS for valid request");
    }
}