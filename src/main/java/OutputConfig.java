package main.java;

import java.io.IOException;

import main.grpc.NumberChain;

// returns an iterable from the user, and passed to computeEngine
public interface OutputConfig {
    String getFilePath();
    void writeResults(Iterable<NumberChain> chains, String delimiter) throws IOException;
}