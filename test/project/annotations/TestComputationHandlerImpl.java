package project.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.mockito.Mockito;

import main.java.ComputationHandlerImpl;
import main.java.ComputeEngine;
import main.java.ComputeEngineRequest;
import main.java.ComputeEngineResult;
import main.java.DataStore;
import main.java.server.DataStoreClient;




public class TestComputationHandlerImpl {

     @Test
        public void constructorShouldThrowExceptionWhenDependenciesAreNull() {
            ComputeEngine mockEngine = Mockito.mock(ComputeEngine.class);
            DataStoreClient mockDataStoreClient = Mockito.mock(DataStoreClient.class);

            assertThrows(IllegalArgumentException.class, () -> new ComputationHandlerImpl(null, mockDataStoreClient));
            assertThrows(IllegalArgumentException.class, () -> new ComputationHandlerImpl(mockEngine, null));
            assertThrows(IllegalArgumentException.class, () -> new ComputationHandlerImpl(null, null));
        }

        @Test
        public void computeShouldFailForInvalidRequest() {
            ComputeEngine mockEngine = Mockito.mock(ComputeEngine.class);
            DataStoreClient mockDataStoreClient = Mockito.mock(DataStoreClient.class);
            ComputationHandlerImpl handler = new ComputationHandlerImpl(mockEngine, mockDataStoreClient);

            ComputeEngineRequest invalidRequest = new ComputeEngineRequest(null, null, ",");
            assertEquals(ComputeEngineResult.FAIL, handler.compute(invalidRequest));
        }
    }