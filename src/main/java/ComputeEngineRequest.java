package main.java;

public class ComputeEngineRequest {
    private static final char DEFAULT_DELIMITER = ';';
    private InputConfig input;
    private OutputConfig output;
    private final char delimiter;

    public ComputeEngineRequest(InputConfig input, OutputConfig output) {
        this(input, output, DEFAULT_DELIMITER);
    }

    public ComputeEngineRequest(InputConfig input, OutputConfig output, char delimiter) {
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

    @Override
    public String toString() {
        return "ComputeRequest: InputConfig=" + input + "OutputConfig=" + output + ", delimiter=" + delimiter;
    }
}