package main.java;

import main.java.project.annotations.ConceptualAPI;

@ConceptualAPI
public interface ComputeEngine {
	
	// method for computation(Takes in a number returns a string
	String compute(int number);

}
