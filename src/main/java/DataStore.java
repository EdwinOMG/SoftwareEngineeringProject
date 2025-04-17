package main.java;

//writes ther esults to outputconfig, which defines how the output is handled
public interface DataStore {
    Iterable<Integer> read(InputConfig inputConfig);
    OutputResult appendResult(OutputConfig output, DigitChains chains, char delimiter);
    OutputResult writeResults(String filePath, DigitChains chains, String delimiter); 
    Iterable<Integer> read(String filePath); // THIS IS FOR FILEPATH

}