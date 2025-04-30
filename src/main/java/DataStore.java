package main.java;

import java.util.List;

import main.grpc.NumberChain;
import main.java.project.annotations.ProcessAPI;

//writes ther esults to outputconfig, which defines how the output is handled
@ProcessAPI
public interface DataStore {
    Iterable<Integer> read(InputConfig inputConfig);
    OutputResult appendResult(OutputConfig output, DigitChains chains, char delimiter);
    OutputResult writeResults(String filePath, List<NumberChain> list, String delimiter); 
    Iterable<Integer> read(String filePath); // THIS IS FOR FILEPATH

}