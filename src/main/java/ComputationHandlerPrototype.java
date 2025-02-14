package main.java;

import main.java.project.annotations.NetworkAPIPrototype;

public class ComputationHandlerPrototype {
	
	@NetworkAPIPrototype 
	public void prototype(ComputationHandler apiToCall) {
		/** - Using an anonymous inner class.
		    - CLient will get input data from various sources 
		      (e.g., List<Integers>, a file, or a database). **/
		InputConfig inputConfig = new InputConfig() {
		};

		/** -Using null for OutputConfig as a placeholder until the implementation is
		    decided.**/
		OutputConfig outputConfig = null;

		/** - ComputeRequest could be an interface with an anonymous inner class,
		      allowing flexibility in defining the default delimiter.
		      Alternatively, an overloaded constructor could handle this.**/
		.
		ComputeRequest request = new ComputeRequest(inputConfig, outputConfig, ',');

		/** - Execute the computation with the assembled inputs. **/		
		ComputeResult result = apiToCall.compute(request);

		/** - Using an enum to wrap a boolean success value.
		    - This will allow for a more detailed definition of what a failure is. **/
		if (result.getStatus().isSuccess()) {
			System.out.println("Much Success ");
		}

		// This completes the workflow from the client�s perspective.
	}
}