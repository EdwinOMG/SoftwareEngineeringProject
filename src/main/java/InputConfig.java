package main.java;

// returns an iterable from the user, and passed to computeEngine
public interface InputConfig {
    Iterable<Integer> getInput();
}	