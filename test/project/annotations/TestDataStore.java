package project.annotations;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import main.java.DataStore;
import main.java.InputConfig;
import main.java.OutputConfig;
import main.java.OutputResult;
public class TestDataStore {
    private DataStore dataStore;
    private InputConfig mockInputConfig;
    private OutputConfig mockOutputConfig;
    private OutputResult mockOutputResult;
    @Before
    public void setUp() { //interfaces used in data store mockInputConfig = mock(InputConfig.class); mockOutputConfig = mock(OutputConfig.class); mockOutputResult = mock(OutputResult.class); // creates data store api dataStore = mock(DataStore.class); }
        @Test
        public void testReadMethodReturnsIterable() {
            Iterable<Integer> mockData = List.of(44);
            when(dataStore.read(mockInputConfig)).thenReturn(mockData);
            Iterable<Integer> result = dataStore.read(mockInputConfig);
            assertNotNull(result);
            assertEquals(mockData, result);
        }

        @Test 
        public void testAppendResult() {
            when(dataStore.appendResult(mockOutputConfig, List.of(44))).thenReturn(mockOutputResult);
            when(mockOutputResult.getStatus()).thenReturn(OutputResult.ShowResultStatus.SUCCESS);
            OutputResult result = dataStore.appendResult(mockOutputConfig, List.of(44));
            assertNotNull(result);
            assertEquals(OutputResult.ShowResultStatus.SUCCESS, result.getStatus());
        }

    }
