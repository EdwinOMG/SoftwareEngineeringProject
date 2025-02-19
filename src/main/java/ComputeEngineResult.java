package main.java;

public interface ComputeEngineResult {
	// shows us whether the computation succeeded
	ComputeEngineResultStatus getStatus();
	
	static ComputeEngineResult SUCCESS = () -> ComputeEngineResultStatus.SUCCESS;
	static ComputeEngineResult FAIL = () -> ComputeEngineResultStatus.FAIL;
	
	public static enum ComputeEngineResultStatus {
		SUCCESS,
		FAIL;

		boolean isSuccess() {
			// TODO Auto-generated method stub
			return false;
		}
	}
		
}
