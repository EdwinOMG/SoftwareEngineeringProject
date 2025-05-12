package test.project.annotations;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import main.java.ComputationHandler;
import main.java.ComputationHandlerImpl;
import main.java.ComputeEngine;
import main.java.ComputeEngineRequest;
import main.java.DataStore;
import main.java.OptimizedComputationHandlerImpl;
import src.main.java.ParallelComputeEngineImpl;

public class BenchmarkTest {

    private final List<Integer> testData = IntStream.rangeClosed(1, 10_000).boxed().toList();

    @Test
    public void benchmarkComputeEngines() {
        ComputeEngine original = new ComputeEngineImpl(); // assuming this is the single-threaded version
        ComputeEngine optimized = new ParallelComputeEngineImpl(); // new parallel version

        long timeOriginal = timeCompute(original);
        long timeOptimized = timeCompute(optimized);

        double improvement = ((double) (timeOriginal - timeOptimized) / timeOriginal) * 100;
        System.out.printf("ComputeEngine - Original: %dms, Optimized: %dms, Improvement: %.2f%%%n",
                timeOriginal, timeOptimized, improvement);

        assertTrue(improvement >= 10, "ParallelComputeEngineImpl should be at least 10% faster");
    }

    @Test
    public void benchmarkComputationHandlers() {
        ComputeEngine originalEngine = new ComputeEngineImpl();
        ComputeEngine optimizedEngine = new ParallelComputeEngineImpl();

        DataStore dummyDataStore = new InMemoryDataStore();

        ComputationHandler original = new ComputationHandlerImpl(originalEngine, dummyDataStore);
        ComputationHandler optimized = new OptimizedComputationHandlerImpl(optimizedEngine, dummyDataStore, Runtime.getRuntime().availableProcessors());

        ComputeEngineRequest request = new ComputeEngineRequest(() -> testData, (chains, delimiter) -> {
        });

        long timeOriginal = timeCoordinator(original, request);
        long timeOptimized = timeCoordinator(optimized, request);

        double improvement = ((double) (timeOriginal - timeOptimized) / timeOriginal) * 100;
        System.out.printf("Coordinator - Original: %dms, Optimized: %dms, Improvement: %.2f%%%n",
                timeOriginal, timeOptimized, improvement);

        assertTrue(improvement >= 10, "OptimizedComputationHandlerImpl should be at least 10% faster");
    }

    private long timeCompute(ComputeEngine engine) {
        long start = System.nanoTime();
        engine.compute(testData);
        return (System.nanoTime() - start) / 1_000_000;
    }

    private long timeCoordinator(ComputationHandler handler, ComputeEngineRequest request) {
        long start = System.nanoTime();
        handler.compute(request);
        return (System.nanoTime() - start) / 1_000_000;
    }

    // Minimal DataStore for testing that avoids I/O
    static class InMemoryDataStore implements DataStore {
        private final AtomicInteger counter = new AtomicInteger();

        @Override
        public Iterable<Integer> readFile(String filePath) {
            return IntStream.rangeClosed(1, 10_000).boxed().toList();
        }

        @Override
        public void appendResult(OutputConfig output, DigitChains chains, String delimiter) {
            counter.incrementAndGet();
        }

        @Override
        public void writeResult(int input, int result) {
            // No-op
        }
    }
}
