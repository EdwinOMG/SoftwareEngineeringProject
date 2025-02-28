package main.java;

public interface OutputConfig {
    String getFilePath();
    void writeOutput(Integer output);  // Add this method if it doesn't exist
    Iterable<Integer> getOutput();
}