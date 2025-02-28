package main.java;

public class ComputeEngineResultImpl implements ComputeEngineResult {
    private final ShowResultStatus status;

    public ComputeEngineResultImpl(ShowResultStatus status) {
        this.status = status;
    }

    @Override
    public ShowResultStatus getStatus() {
        return status;
    }
}
