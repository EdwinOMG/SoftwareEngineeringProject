package main.java;

import java.util.ArrayList;

public class ComputeEngineImpl  implements ComputeEngine{
	
	
	// method to square each digit then return the sum to the compute() method
	private int squareDigitSum(int i) {
		int sum = 0;
		while(i > 0) {
			int digit = i % 10; // get last digit
			sum += (digit * digit); //square digit and add it 
			i = i / 10; // get rid of digit
		}
		return sum;
	}
	
	// This is where square digit chain algorithm will be.
	@Override
	public Iterable<Integer> compute(int num){
		// create Iterable<Integer>
		ArrayList<Integer> digitChain = new ArrayList<>();
		digitChain.add(num);
		while(num != 89 && num != 1){
			//method that returns sum of squared digits
			num = squareDigitSum(num);
			//add total sum to ArrayList<Integer>
			digitChain.add(num);
		}
		return digitChain;

	}
	
	

	
}