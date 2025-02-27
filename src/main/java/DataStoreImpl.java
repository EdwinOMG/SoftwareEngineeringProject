package main.java;


public class DataStoreImpl implements DataStore{
	//It <integer> for flexibility
	@Override
	public Iterable<Integer> read(InputConfig inputConfig) {
		return null;
	}

	
	//output is a failure status since theres nothing computing
	@Override
	public OutputResult appendResult(OutputConfig output, Iterable<Integer> result, char delimiter) {
		return new OutputResult() {
			@Override
			public ShowResultStatus getStatus() {
				return ShowResultStatus.FAILURE;
			}
		};
	}

}