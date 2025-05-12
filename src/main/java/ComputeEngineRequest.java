package main.java;

public class ComputeEngineRequest {
    private InputConfig input;
    private OutputConfig output;
    private final String delimiter;

    
    public ComputeEngineRequest(InputConfig input, OutputConfig output, String c) {
        this.input = input;
        this.output = output;
        this.delimiter = c;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public InputConfig getInput() {
        return input;
    }

    public OutputConfig getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "ComputeRequest: InputConfig=" + input + "OutputConfig=" + output + ", delimiter=" + delimiter;
    }
}