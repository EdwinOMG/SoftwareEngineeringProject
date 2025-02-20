package main.java;

import main.java.project.annotations.ConceptualAPIPrototype;
// takes in instance of computEengine
public class PrototypeComputeEngine {

	
	@ConceptualAPIPrototype
	public void prototype(ComputeEngine engine) {
		Iterable<Integer> result = engine.compute(5); //test how it processes an input
		
	}
}
