package test.project.annotations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import org.mockito.Mockito;

class ComputationHandlerImplTest {

    @Test
    void constructorShouldThrowExceptionWhenDependenciesAreNull() {
        ComputeEngine mockEngine = Mockito.mock(ComputeEngine.class);
        DataStore mockDataStore = Mockito.mock(DataStore.class);
        
        assertThrows(IllegalArgumentException.class, () -> new ComputationHandlerImpl(null, mockDataStore));
        assertThrows(IllegalArgumentException.class, () -> new ComputationHandlerImpl(mockEngine, null));
        assertThrows(IllegalArgumentException.class, () -> new ComputationHandlerImpl(null, null));
    }

    @Test
    void computeShouldFailForInvalidRequest() {
        ComputeEngine mockEngine = Mockito.mock(ComputeEngine.class);
        DataStore mockDataStore = Mockito.mock(DataStore.class);
        ComputationHandlerImpl handler = new ComputationHandlerImpl(mockEngine, mockDataStore);
        
        ComputeEngineRequest invalidRequest = new ComputeEngineRequest(null, null, ',');
        assertEquals(ComputeEngineResult.FAIL, handler.compute(invalidRequest));
    }
}

