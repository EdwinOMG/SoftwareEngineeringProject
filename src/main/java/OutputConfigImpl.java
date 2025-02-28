package main.java;

public class OutputConfigImpl implements OutputConfig {
    private final String filePath;

    public OutputConfigImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }
}
