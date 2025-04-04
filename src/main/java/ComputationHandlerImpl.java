package main.java;

public class ComputationHandlerImpl implements ComputationHandler {
    private final ComputeEngine computeEngine;
    private final DataStore dataStore;

    public ComputationHandlerImpl(ComputeEngine computeEngine, DataStore dataStore) {
        if (computeEngine == null || dataStore == null) {
            throw new IllegalArgumentException("ComputeEngine and DataStore can't be null");
        }
        this.computeEngine = computeEngine;
        this.dataStore = dataStore;
    }

    @Override
    public ComputeEngineResult compute(ComputeEngineRequest request) {
        return ComputationHandlerUtil.processRequest(computeEngine, dataStore, request);
    }
}
