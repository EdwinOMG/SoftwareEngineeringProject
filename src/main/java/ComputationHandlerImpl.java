package main.java;

import main.java.ComputeEngineResult;
import main.java.OutputResult.ShowResultStatus;

public class ComputationHandlerImpl implements ComputationHandler {

    private final ComputeEngine computeEngine;
    private final DataStore dataStore;

    public ComputationHandlerImpl(ComputeEngine computeEngine, DataStore dataStore) {
    	if (computeEngine == null || dataStore == null) {
    		throw new IllegalArgumentException("ComputeeEngine and DataStore can't be null");
    	}
        this.computeEngine = computeEngine;
        this.dataStore = dataStore;
    }

    @Override
    public ComputeEngineResult compute(ComputeEngineRequest request) {
    	if (request == null || request.getInput() == null || request.getOutput() == null) {
    		return ComputeEngineResult.FAIL;
    	}
    	try {
    		
    	
        Iterable<Integer> inputData = dataStore.read(request.getInput());
        if (inputData == null) {
        	return ComputeEngineResult.FAIL;
        }
        DigitChains chains = computeEngine.compute(inputData);
        if (chains == null) {
        	return ComputeEngineResult.FAIL;
        }
        OutputResult outputResult = dataStore.appendResult(request.getOutput(), chains, request.getDelimiter());
        if (outputResult.getStatus() != ShowResultStatus.SUCCESS) {
        	return ComputeEngineResult.FAIL;
        }
        return outputResult.getStatus() == ShowResultStatus.SUCCESS
                ? ComputeEngineResult.SUCCESS
                : ComputeEngineResult.FAIL;
    	}
    	catch (Exception e) { // catch unexpected exceptions
    		return ComputeEngineResult.FAIL;
    	}
    }
}  
//delete this later