package main.java;

public interface DataStore {
    Iterable<Integer> read(InputConfig inputConfig);
    OutputResult appendResult(OutputConfig output, Iterable<Integer> result, char delimiter);
}
