package main.java;

import main.java.project.annotations.ConceptualAPI;
// Processes the input into a chain(2d array wrapper)
@ConceptualAPI
public interface ComputeEngine {
    DigitChains compute(Iterable<Integer> input);
}