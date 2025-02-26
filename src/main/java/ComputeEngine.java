package main.java;

import main.java.project.annotations.ConceptualAPI;

@ConceptualAPI
public interface ComputeEngine {

	Iterable<Integer> compute(Iterable<Integer> data);
	
}
