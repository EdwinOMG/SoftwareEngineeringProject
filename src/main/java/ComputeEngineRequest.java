package main.java;
// request to the engine
public class ComputeEngineRequest {
	// input and output config to store the configuration
	//has getters and setters
	private InputConfig input;
	private OutputConfig output;
	
	public ComputeEngineRequest(InputConfig input, OutputConfig output) {
			this.input = input;
			this.output = output;
		
	}
	
	public InputConfig getInput() {
			return input;
	}
	
	public OutputConfig getOutput() {
		return output;
	}

}
