package src.main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelComputeEngineImpl implements ComputeEngine {
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public DigitChains compute(Iterable<Integer> inputList) {
        List<Callable<List<Integer>>> tasks = new ArrayList<>();
        for (Integer num : inputList) {
            tasks.add(() -> computeChain(num));
        }

        try {
            List<Future<List<Integer>>> results = executor.invokeAll(tasks);
            List<Iterable<Integer>> chains = new ArrayList<>();

            for (Future<List<Integer>> result : results) {
                try {
                    chains.add(result.get());
                } catch (ExecutionException e) {
                    throw new RuntimeException("Computation task failed", e.getCause());
                }
            }

            return new DigitChains(chains);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // preserve interrupt status
            throw new RuntimeException("Thread was interrupted while waiting for tasks to complete", e);
        }

    }

    private List<Integer> computeChain(int num) {
        List<Integer> chain = new ArrayList<>();
        while (num != 1 && num != 89) {
            chain.add(num);
            num = squareDigitSum(num);
        }
        chain.add(num);
        return chain;
    }

    private int squareDigitSum(int num) {
        int sum = 0;
        while (num > 0) {
            int digit = num % 10;
            sum += digit * digit;
            num /= 10;
        }
        return sum;
    }
}

