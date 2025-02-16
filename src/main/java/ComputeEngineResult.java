package main.java;

public interface ComputeEngineResult {
	
	ComputeEngineResultStatus getStatus();
	
	static ComputeEngineResult SUCCESS = () -> ComputeEngineResultStatus.SUCCESS;
	static ComputeEngineResult FAIL = () -> ComputeEngineResultStatus.FAIL;
	
	public static enum ComputeEngineResultStatus {
		SUCCESS,
		FAIL;
	}
		
}
