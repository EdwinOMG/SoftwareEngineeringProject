package main.java;

public class OutputResultImpl implements OutputResult {
    private final ShowResultStatus status;

    public OutputResultImpl(ShowResultStatus status) {
        this.status = status;
    }

    @Override
    public ShowResultStatus getStatus() {
        return status;
    }
}
