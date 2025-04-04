package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;


public class MultiThreadedComputationHandlerImpl implements ComputationHandler {
    private final ComputeEngine computeEngine;
    private final DataStore dataStore;
    private final ExecutorService executor;
    private static final int MAX_THREADS = 4; // Adjust as needed

    public MultiThreadedComputationHandlerImpl(ComputeEngine computeEngine, DataStore dataStore) {
        if (computeEngine == null || dataStore == null) {
            throw new IllegalArgumentException("ComputeEngine and DataStore can't be null");
        }
        this.computeEngine = computeEngine;
        this.dataStore = dataStore;
        this.executor = Executors.newFixedThreadPool(MAX_THREADS);
    }

    @Override
    public ComputeEngineResult compute(ComputeEngineRequest request) {
        if (request == null || request.getInput() == null || request.getOutput() == null) {
            return ComputeEngineResult.FAIL;
        }

        try {
            Iterable<Integer> inputData = dataStore.read(request.getInput());
            if (inputData == null) return ComputeEngineResult.FAIL;

            List<Future<Iterable<Integer>>> futures = new ArrayList<>();

            for (Integer number : inputData) {
                futures.add(executor.submit(() -> computeEngine.compute(List.of(number)).iterator().next()));
            }

            List<Iterable<Integer>> allChains = new ArrayList<>();
            for (Future<Iterable<Integer>> future : futures) {
                allChains.add(future.get()); // wait for each to finish
            }

            DigitChains chains = new DigitChains(allChains);
            OutputResult outputResult = dataStore.appendResult(request.getOutput(), chains, request.getDelimiter());

            return outputResult.getStatus() == OutputResult.ShowResultStatus.SUCCESS
                    ? ComputeEngineResult.SUCCESS
                    : ComputeEngineResult.FAIL;

        } catch (Exception e) {
            return ComputeEngineResult.FAIL;
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}
