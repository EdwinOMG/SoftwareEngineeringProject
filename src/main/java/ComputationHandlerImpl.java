package main.java;

import main.java.ComputeEngineResult;
import main.java.OutputResult.ShowResultStatus;

public class ComputationHandlerImpl implements ComputationHandler {

    private final ComputeEngine computeEngine;
    private final DataStore dataStore;

    public ComputationHandlerImpl(ComputeEngine computeEngine, DataStore dataStore) {
        this.computeEngine = computeEngine;
        this.dataStore = dataStore;
    }

    @Override
    public ComputeEngineResult compute(ComputeEngineRequest request) {
        Iterable<Integer> inputData = dataStore.read(request.getInput());
        DigitChains chains = computeEngine.compute(inputData);
        OutputResult outputResult = dataStore.appendResult(request.getOutput(), chains, request.getDelimiter());
        return outputResult.getStatus() == ShowResultStatus.SUCCESS
                ? ComputeEngineResult.SUCCESS
                : ComputeEngineResult.FAIL;
    }
}