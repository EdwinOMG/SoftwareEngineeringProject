package project.annotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

import main.java.ComputationHandler;
import main.java.ComputeEngineRequest;
import main.java.ComputeEngineResult;
import main.java.InputConfig;
import main.java.OutputConfig;


public class TestUser {

	// TODO 3: change the type of this variable to the name you're using for your
	// @NetworkAPI interface; also update the parameter passed to the constructor
	private final ComputationHandler coordinator;

	public TestUser(ComputationHandler coordinator) {
		this.coordinator = coordinator;
	}

	public void run(String outputPath) {
		char delimiter = ';';
		String inputPath = "test/project.annotations" + File.separatorChar + "testInputFile.test";

		// TODO 4: Call the appropriate method(s) on the coordinator to get it to 
		// run the compute job specified by inputPath, outputPath, and delimiter


		InputConfig inputConfig = new InputConfig() {

	        @Override
	        public Iterable<Integer> getInput() {
	            try {
	            	if (!Files.exists(Paths.get(inputPath))) {
	                    throw new RuntimeException("Input file not found: " + inputPath);
	                }
	                return Files.readAllLines(Paths.get(inputPath))
	                            .stream()
	                            .map(Integer::parseInt)
	                            .collect(Collectors.toList());
	            } catch (IOException e) {
	                throw new RuntimeException("Failed to read input file: " + inputPath, e);
	            } catch (NumberFormatException e) {
	                throw new RuntimeException("Invalid number format in input file", e);
	            }
	        }
	    };

	    OutputConfig outputConfig = new OutputConfig() {
	        @Override
	        public void writeOutput(Integer output) {
	            try {
	                Files.writeString(
	                    Paths.get(outputPath),
	                    output.toString() + delimiter,
	                    StandardOpenOption.CREATE,
	                    StandardOpenOption.APPEND
	                );
	            } catch (IOException e) {
	                throw new RuntimeException("Failed to write to output file: " + outputPath, e);
	            }
	        }
	    };

	    ComputeEngineRequest request = new ComputeEngineRequest(inputConfig, outputConfig, delimiter);
	    ComputeEngineResult result = coordinator.compute(request);

	    if (result != ComputeEngineResult.SUCCESS) {
	        throw new RuntimeException("Computation failed with result: " + result);
	    }
	}

}	
