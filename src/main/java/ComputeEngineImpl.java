package main.java;

import java.util.List;

public class ComputeEngineImpl  implements ComputeEngine{

	@Override
	public Iterable<Integer> compute(int i){
		return List.of(i); //List of just i
	}

}