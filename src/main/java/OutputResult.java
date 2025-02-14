package main.java;

public interface OutputResult {

	ShowResultStatus getStatus();
	
	// defines an enum to indicate if appendresult was a success
	// basically true or false but in a helpful way.
	public static enum ShowResultStatus {
		SUCCESS,
		FAILURE;
	}
}
