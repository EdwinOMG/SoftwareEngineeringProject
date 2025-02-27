package main.java;
// request to the engine
public class ComputeEngineRequest {
	private static final char DEFAULT_DELIMITER = ';';
	// input and output config to store the configuration
	//has getters and setters
	//basically a data wrapper
	private InputConfig input;
	private OutputConfig output;
	private final char delimiter;
	
	public ComputeEngineRequest(InputConfig input, OutputConfig output) {
			this(input, output, DEFAULT_DELIMITER);
		
	}
	
	public ComputeEngineRequest(InputConfig input, OutputConfig outputConfig, char delimiter) {
		this.input = input;
		this.output = output;
		this.delimiter = delimiter;
	}
	
	public char getDelimiter() {
		return delimiter;
	}
	public InputConfig getInput() {
			return input;
	}
	
	public OutputConfig getOutput() {
		return output;
	}

	// overrides string method so we can print out a compute request 
	@Override
	public String toString() {
		return "ComputeRequest: InputConfig=" + input + "OutputConfig=" + output + ", delimiter=" + delimiter;
	}
}
