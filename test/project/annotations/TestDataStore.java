package project.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import main.java.DataStore;
import main.java.DigitChains;
import main.java.InputConfig;
import main.java.OutputConfig;
import main.java.OutputResult;

public class TestDataStore {
    private DataStore dataStore;
    private InputConfig mockInputConfig;
    private OutputConfig mockOutputConfig;
    private OutputResult mockOutputResult;

    @Before
    public void setUp() {
        mockInputConfig = mock(InputConfig.class);
        mockOutputConfig = mock(OutputConfig.class);
        mockOutputResult = mock(OutputResult.class);
        dataStore = mock(DataStore.class);
    }

    @Test
    public void testReadMethodReturnsIterable() {
        // Arrange: Define the expected data
        Iterable<Integer> mockData = List.of(44);

        // Stub the read method to return the mock data
        when(dataStore.read(mockInputConfig)).thenReturn(mockData);

        // Act: Call the read method
        Iterable<Integer> result = dataStore.read(mockInputConfig);

        // Assert: Verify the result
        assertNotNull("Result should not be null", result);
        assertEquals("Returned data should match the mock data", mockData, result);
    }

    @Test
    public void testAppendResult() {
        // Arrange: Define the input data
        DigitChains mockChains = new DigitChains(List.of(List.of(44)));

        // Stub the appendResult method to return the mock output result
        when(dataStore.appendResult(mockOutputConfig, mockChains, ';')).thenReturn(mockOutputResult);

        // Stub the getStatus method to return SUCCESS
        when(mockOutputResult.getStatus()).thenReturn(OutputResult.ShowResultStatus.SUCCESS);

        // Act: Call the appendResult method
        OutputResult result = dataStore.appendResult(mockOutputConfig, mockChains, ';');

        // Assert: Verify the result
        assertNotNull("Result should not be null", result);
        assertEquals("Status should be SUCCESS", OutputResult.ShowResultStatus.SUCCESS, result.getStatus());
    }
}