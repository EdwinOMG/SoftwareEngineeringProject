package main.java;

import java.util.ArrayList;
import java.util.List;

public class ComputeEngineImpl implements ComputeEngine {

    private int squareDigitSum(int i) {
        int sum = 0;
        while (i > 0) {
            int digit = i % 10;
            sum += (digit * digit);
            i = i / 10;
        }
        return sum;
    }

    private List<Integer> computeChain(int num) {
        List<Integer> chain = new ArrayList<>();
        chain.add(num);
        while (num != 89 && num != 1) {
            num = squareDigitSum(num);
            chain.add(num);
        }
        return chain;
    }

    @Override
    public DigitChains compute(Iterable<Integer> inputList) {
        List<Iterable<Integer>> result = new ArrayList<>();
        for (int num : inputList) {
            List<Integer> chain = computeChain(num);
            result.add(chain);
        }
        return new DigitChains(result);
    }
}