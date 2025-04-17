package main.java;

public class FileOutputConfig implements OutputConfig {
    private final String filePath;
    public FileOutputConfig(String filePath) { this.filePath = filePath; }
    public String getFilePath() { return filePath; }

    @Override
    public void writeOutput(Integer output) {
        throw new UnsupportedOperationException("Used only with gRPC");
    }
}
