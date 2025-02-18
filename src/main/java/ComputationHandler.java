package main.java;

import main.java.project.annotations.NetworkAPI;

/** - A network API that facilitates communication between the user and compute engine.
    - This design will use a single request wrapper to encapsulate all required user inputs.
    - Also, individual parameters could be specified, by using method overloading to handle the default delimiter. 
    - This method is similar to Builder pattern. **/

@NetworkAPI 
public interface ComputationHandler {
	
    ComputeEngineResult compute(ComputeEngineRequest request);
}
