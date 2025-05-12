package main.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import main.grpc.NumberChain;

public class DataStoreImpl implements DataStore {

    @Override
    public Iterable<Integer> read(InputConfig inputConfig) {
    	if (inputConfig == null) {
    		throw new IllegalArgumentException("InputConfig can't be null.");
    	}
    	
    	try {
    		Iterable<Integer> inputData = inputConfig.getInput();
    		if (inputData == null) {
    			throw new IllegalStateException("InputConfig returned null");
    		}
    		return inputData;
    	} catch (Exception e) {
    		throw new RuntimeException("Failed to read data: " + e.getMessage(), e);
    	}
    }

    @Override
    public OutputResult appendResult(OutputConfig outputConfig, DigitChains chains, String delimiter) {
       if (outputConfig == null || chains == null) {
    	   throw new IllegalArgumentException("OutputConfig and DigitChains can't be null");
       }
       try {
    	List<NumberChain> numberChains = new ArrayList<>();
    	for (Iterable<Integer> chain : chains) {
    		NumberChain.Builder builder = NumberChain.newBuilder();
    		chain.forEach(builder::addNumbers);
    		numberChains.add(builder.build());
    	}
    	
    	outputConfig.writeResults(numberChains, delimiter);
    	
    	return new OutputResult() {
    		@Override 
    		public ShowResultStatus getStatus() {
    			return ShowResultStatus.SUCCESS;
    		}
    	};
       } catch (IOException e) {
    	   return new OutputResult() {
    		   @Override
    		   public ShowResultStatus getStatus() {
    			   return ShowResultStatus.FAILURE;
    		   }
    	   };
       }
}

    @Override
    public OutputResult writeResults(String filePath, List<NumberChain> list, String delimiter) {
    	try {
            List<String> lines = new ArrayList<>();
            for (NumberChain chain : list) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < chain.getNumbersCount(); i++) {
                    sb.append(chain.getNumbers(i));
                    if (i < chain.getNumbersCount() - 1) {
                        sb.append(delimiter);
                    }
                }
                lines.add(sb.toString());
            }
            try {
            java.nio.file.Files.write(java.nio.file.Paths.get(filePath), lines);
            } catch (FileNotFoundException e) {
            	System.out.println("Error finding file during writeResults: " + e);
            } catch (IOException e) {
            	System.out.println("General I/O error writing results: " + e);
            }
            return new OutputResult() {
                @Override
                public ShowResultStatus getStatus() {
                    return ShowResultStatus.SUCCESS;
                }
            };
        } catch (Exception e) {
            throw new RuntimeException("Failed to write results: " + e.getMessage(), e);
        }
    }
    
    
    @Override
    public Iterable<Integer> readFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            List<Integer> numbers = new ArrayList<>();
            
            for (String line : lines) {
                String[] parts = line.split("[,\\s]+"); // Split by comma or whitespace
                for (String part : parts) {
                    if (!part.isEmpty()) {
                        numbers.add(Integer.parseInt(part.trim()));
                    }
                }
            }
            
            return numbers;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filePath, e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format in file: " + filePath, e);
        }
    }
    
}
//delete this later