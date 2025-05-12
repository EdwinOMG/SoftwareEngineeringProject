package main.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.grpc.NumberChain;
import main.java.server.DataStoreClient;

public class ComputationHandlerImpl implements ComputationHandler {
    private final ComputeEngine computeEngine;
    private final DataStoreClient dataStoreClient;

    public ComputationHandlerImpl(ComputeEngine computeEngine, DataStoreClient dataStoreClient) {
        if (computeEngine == null || dataStoreClient == null) {
            throw new IllegalArgumentException("ComputeEngine and DataStore can't be null");
        }
        this.computeEngine = computeEngine;
        this.dataStoreClient = dataStoreClient;
    }

    @Override
    public ComputeEngineResult compute(ComputeEngineRequest request) {
    	try {
    	Iterable<Integer> numbers = request.getInput().getInput() != null
    			? request.getInput().getInput()
    			: dataStoreClient.readFile(request.getInput().getFilePath());
    	DigitChains chains = computeEngine.compute(numbers);
    	
    	if (request.getOutput() != null && request.getOutput().getFilePath() != null) {
    		dataStoreClient.writeFile(
    				request.getOutput().getFilePath(), 
    				convertToNumberChains(chains), 
    				request.getDelimiter()
    				);
    	}
    	
    	return new ComputeEngineResult() {
            private final DigitChains resultChains = chains;
            
            @Override
            public ComputeEngineResultStatus getStatus() {
                return ComputeEngineResultStatus.SUCCESS;
            }

            @Override
            public String getFailureMessage() {
                return "";
            }
            
            // Optional: Add method to access chains if needed
            public DigitChains getChains() {
                return resultChains;
            }
        };
        
    } catch (IOException e) {
        return ComputeEngineResult.FAIL; // Use the static FAIL instance
        
    } catch (Exception e) {
        return new ComputeEngineResult() {
            @Override
            public ComputeEngineResultStatus getStatus() {
                return ComputeEngineResultStatus.INVALID_REQUEST;
            }

            @Override
            public String getFailureMessage() {
                return e.getMessage();
            }
        };
    }
}

    private Iterable<NumberChain> convertToNumberChains(DigitChains chains) {
        List<NumberChain> result = new ArrayList<>();
        for (Iterable<Integer> chain : chains) {
            NumberChain.Builder builder = NumberChain.newBuilder();
            chain.forEach(builder::addNumbers);
            result.add(builder.build());
        }
        return result;
    }
}
