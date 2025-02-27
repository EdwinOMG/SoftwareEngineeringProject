package main.java;

import main.java.project.annotations.ProcessAPI;

@ProcessAPI
public interface DataStore {
	// read data based on some configuration
	Iterable<Integer> read(InputConfig inputConfig);

	//process and write data somewhere
	OutputResult appendResult(OutputConfig output, Iterable<Integer> result, char delimiter);

	
	
}
