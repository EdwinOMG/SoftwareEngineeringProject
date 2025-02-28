package main.java;

public interface DataStore {
    Iterable<Integer> read(InputConfig inputConfig);
    OutputResult appendResult(OutputConfig output, DigitChains chains, char delimiter);
}