package main.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

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
    public OutputResult appendResult(OutputConfig outputConfig, DigitChains chains, char delimiter) {
       if (outputConfig == null || chains == null) {
    	   throw new IllegalArgumentException("OutputConfig and DigitChains can't be null");
       }
       try {
    	for (Iterable<Integer> chain : chains) {
            for (Integer number : chain) {
                outputConfig.writeOutput(number); // Write each number to the output config
                outputConfig.writeOutput((int) delimiter); // Write the delimiter as an integer
            }
        }

        return new OutputResult() {
            @Override
            public ShowResultStatus getStatus() {
                return ShowResultStatus.SUCCESS;
            }
        };
    } catch (Exception e) {
    	throw new RuntimeException("Failed to append");
    }
}
    
    @Override
    public OutputResult writeResults(String filePath, DigitChains chains, String delimiter) {
        try {
            List<String> lines = new ArrayList<>();
            for (Iterable<Integer> chain : chains) {
                StringBuilder sb = new StringBuilder();
                for (Integer num : chain) {
                    sb.append(num).append(delimiter);
                }
                lines.add(sb.substring(0, sb.length() - delimiter.length()));
            }
            
            // Write to file (using Java NIO)
            Files.write(Paths.get(filePath), lines, StandardOpenOption.CREATE);
            
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
    public Iterable<Integer> read(String filePath) {
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