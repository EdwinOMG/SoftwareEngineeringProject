package main.java;

public class ComputeEngineRequest {
	
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
