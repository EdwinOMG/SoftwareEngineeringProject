package project.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.Before;

import main.java.ComputationHandlerImpl;
import main.java.ComputeEngine;
import main.java.ComputeEngineRequest;
import main.java.ComputeEngineResult;
import main.java.DataStore;

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

