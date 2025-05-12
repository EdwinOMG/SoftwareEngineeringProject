package main.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import main.java.ComputeEngineResult.ComputeEngineResultStatus;

public class OptimizedComputationHandlerImpl implements ComputationHandler {

    private final ComputeEngine computeEngine;
    private final DataStore dataStore;
    private final ExecutorService executor;

    public OptimizedComputationHandlerImpl(ComputeEngine computeEngine, DataStore dataStore, int threadCount) {
        if (computeEngine == null || dataStore == null) {
            throw new IllegalArgumentException("ComputeEngine and DataStore cannot be null.");
        }
        this.computeEngine = computeEngine;
        this.dataStore = dataStore;
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    @Override
    public ComputeEngineResult compute(ComputeEngineRequest request) {
        try {
            Iterable<Integer> numbers = request.getInput().getInput() != null
                    ? request.getInput().getInput()
                    : dataStore.readFile(request.getInput().getFilePath());

            List<Future<Iterable<Integer>>> futures = new ArrayList<>();
            for (Integer number : numbers) {
                futures.add(executor.submit(new Callable<Iterable<Integer>>() {
                    @Override
                    public Iterable<Integer> call() {
                        DigitChains chains = computeEngine.compute(List.of(number));
                        Iterator<Iterable<Integer>> it = chains.iterator();
                        return it.hasNext() ? it.next() : List.of();
                    }
                }));
            }

            List<Iterable<Integer>> allChains = new ArrayList<>();
            for (Future<Iterable<Integer>> future : futures) {
                allChains.add(future.get());
            }

            DigitChains finalChains = new DigitChains(allChains);

            OutputConfig output = request.getOutput();
            if (output != null) {
                dataStore.appendResult(output, finalChains, request.getDelimiter());
            }

            return new ComputeEngineResult() {
                @Override
                public ComputeEngineResultStatus getStatus() {
                    return ComputeEngineResultStatus.SUCCESS;
                }

                @Override
                public String getFailureMessage() {
                    return "";
                }
            };

        } catch (IOException e) {
            e.printStackTrace();
            return ComputeEngineResult.FAIL;

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new ComputeEngineResult() {
                @Override
                public ComputeEngineResultStatus getStatus() {
                    return ComputeEngineResultStatus.INVALID_REQUEST;
                }

                @Override
                public String getFailureMessage() {
                    return e.getMessage();
                }
            };
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}
