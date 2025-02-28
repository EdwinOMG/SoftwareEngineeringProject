package main.java;

import main.java.project.annotations.ConceptualAPI;

@ConceptualAPI
public interface ComputeEngine {
    DigitChains compute(Iterable<Integer> input);
}