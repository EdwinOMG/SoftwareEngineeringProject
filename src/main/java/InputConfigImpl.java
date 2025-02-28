package main.java;

public class InputConfigImpl implements InputConfig {
    private final String filePath;

    public InputConfigImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }
}
