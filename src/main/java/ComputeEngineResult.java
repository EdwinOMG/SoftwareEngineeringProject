package main.java;

public interface ComputeEngineResult {
    static ComputeEngineResult SUCCESS = new ComputeEngineResult() {
        @Override
        public ComputeEngineResultStatus getStatus() {
            return ComputeEngineResultStatus.SUCCESS;
        }

        @Override
        public String getFailureMessage() {
            return "";
        }
    };
    
    static ComputeEngineResult FAIL = new ComputeEngineResult() {
        @Override
        public ComputeEngineResultStatus getStatus() {
            return ComputeEngineResultStatus.FAIL;
        }

        @Override
        public String getFailureMessage() {
            return "";
        }
    };

    ComputeEngineResultStatus getStatus();
    String getFailureMessage();

    public static enum ComputeEngineResultStatus {
        SUCCESS(true),
        FAIL(false),
        INVALID_REQUEST(false);

        private final boolean success;

        private ComputeEngineResultStatus(boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}