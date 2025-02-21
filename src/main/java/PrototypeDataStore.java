package main.java;

import main.java.project.annotations.ProcessAPIPrototype;

public class PrototypeDataStore {
	// datastore is the api
	@ProcessAPIPrototype
	public void prototype(DataStore dataStore) {

		InputConfig inputConfig = new InputConfig();
		
			
		
		
		OutputConfig outputConfig = null;
			
		
	
		// reads in data
		Iterable<Integer> readData = dataStore.read(inputConfig);
			
		//do something with readdata 
		
		// takes in where the data will go, and integers previously read, 
		//stores the data at the output, and passes on a object to indicate if it was successful
		OutputResult outputResult = dataStore.appendResult(outputConfig, readData);

		//if it fails it will print damn...
		if (outputResult.getStatus() != OutputResult.ShowResultStatus.SUCCESS) {
			System.out.println("FAILED");
		}
}
}
