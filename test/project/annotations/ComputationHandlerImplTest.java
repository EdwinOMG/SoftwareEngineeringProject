package test.project.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import src.main.java.ComputationHandlerImpl;
import src.main.java.ComputeEngine;
import src.main.java.ComputeEngineRequest;
import src.main.java.ComputeEngineResult;
import src.main.java.DataStore;

public class ComputationHandlerImplTest {

	 @Test
	    public void constructorShouldThrowExceptionWhenDependenciesAreNull() {
	        ComputeEngine mockEngine = Mockito.mock(ComputeEngine.class);
	        DataStore mockDataStore = Mockito.mock(DataStore.class);
	        
	        assertThrows(IllegalArgumentException.class, () -> new ComputationHandlerImpl(null, mockDataStore));
	        assertThrows(IllegalArgumentException.class, () -> new ComputationHandlerImpl(mockEngine, null));
	        assertThrows(IllegalArgumentException.class, () -> new ComputationHandlerImpl(null, null));
	    }

	    @Test
	    public void computeShouldFailForInvalidRequest() {
	        ComputeEngine mockEngine = Mockito.mock(ComputeEngine.class);
	        DataStore mockDataStore = Mockito.mock(DataStore.class);
	        ComputationHandlerImpl handler = new ComputationHandlerImpl(mockEngine, mockDataStore);
	        
	        ComputeEngineRequest invalidRequest = new ComputeEngineRequest(null, null, ',');
	        assertEquals(ComputeEngineResult.FAIL, handler.compute(invalidRequest));
	    }
	}

