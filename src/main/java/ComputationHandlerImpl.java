package main.java;

public class ComputationHandlerImpl implements ComputationHandler {

	
	private final ComputeEngine computeEngine;
	private final DataStore dataStore;
	
	public ComputationHandlerImpl(ComputeEngine computeEngine, DataStore dataStore) {
		this.computeEngine = computeEngine;
		this.dataStore = dataStore;
	}
	
	@Override
	public ComputeEngineResult compute(ComputeEngineRequest request) {
		
		return null;
	}
	
}