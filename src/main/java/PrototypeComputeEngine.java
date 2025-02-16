package main.java;

import main.java.project.annotations.ConceptualAPIPrototype;

public class PrototypeComputeEngine {

	
	@ConceptualAPIPrototype
	public void prototype(ComputeEngine engine) {
		String result = engine.compute(1);
		
	}
}
