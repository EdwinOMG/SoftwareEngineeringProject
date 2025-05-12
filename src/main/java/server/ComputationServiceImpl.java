package main.java.server;

import io.grpc.stub.StreamObserver;
import main.java.ComputationHandler;
import main.java.ComputeEngineRequest;
import main.java.ComputeEngineResult;
import main.java.InputConfig;
import main.java.OutputConfig;
import main.grpc.ComputationServiceGrpc;
import main.grpc.ComputeRequest;
import main.grpc.ComputeResponse;
import main.grpc.NumberChain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComputationServiceImpl extends ComputationServiceGrpc.ComputationServiceImplBase {
    
    private final ComputationHandler computationHandler;
    private final DataStoreClient dataStoreClient;  // Add this field
    
    public ComputationServiceImpl(ComputationHandler computationHandler, DataStoreClient dataStoreClient) {
        this.computationHandler = computationHandler;
        this.dataStoreClient = dataStoreClient;  // Initialize here
    }

    @Override
    public void compute(ComputeRequest request, StreamObserver<ComputeResponse> responseObserver) {
        try {
            ComputeEngineRequest domainRequest = toDomainRequest(request);
            ComputeEngineResult result = computationHandler.compute(domainRequest);
            responseObserver.onNext(toGrpcResponse(result));
        } catch (Exception e) {
            responseObserver.onNext(errorResponse(
                e instanceof IllegalArgumentException ? 
                    ComputeResponse.Status.INVALID_INPUT :
                    ComputeResponse.Status.COMPUTATION_ERROR,
                e
            ));
        } finally {
            responseObserver.onCompleted();
        }
    }

    private ComputeEngineRequest toDomainRequest(ComputeRequest request) {
        InputConfig input = new InputConfig() {
            @Override 
            public List<Integer> getInput() {
                return request.hasNumbers() ? request.getNumbers().getNumbersList() : null;
            }
            @Override 
            public String getFilePath() {
                return request.hasNumbers() ? null : request.getInputFile();
            }
        };
        
        OutputConfig output = new OutputConfig() {
            @Override 
            public String getFilePath() {
                return request.getOutputFile();
            }
            
            @Override
            public void writeResults(Iterable<NumberChain> chains, String delimiter) throws IOException {
                if (dataStoreClient != null && getFilePath() != null) {
                    List<NumberChain> chainList = new ArrayList<>();
                    chains.forEach(chainList::add);
                    
                    // Write to file using the DataStoreClient
                    dataStoreClient.writeFile(
                        getFilePath(),
                        chainList,
                        delimiter
                    );
                }
            }
        };
        
        String delimiter = request.getDelimiter().isEmpty() ? ";" : request.getDelimiter();
        return new ComputeEngineRequest(input, output, delimiter);
    }

    private ComputeResponse toGrpcResponse(ComputeEngineResult result) {
        ComputeResponse.Builder builder = ComputeResponse.newBuilder()
            .setStatus(result.getStatus() == ComputeEngineResult.ComputeEngineResultStatus.SUCCESS 
                ? ComputeResponse.Status.SUCCESS 
                : ComputeResponse.Status.COMPUTATION_ERROR);
        
        if (result.getStatus() == ComputeEngineResult.ComputeEngineResultStatus.FAIL) {
            builder.setMessage(result.getFailureMessage());
        }
        
        return builder.build();
    }

    private ComputeResponse errorResponse(ComputeResponse.Status status, Exception e) {
        return ComputeResponse.newBuilder()
            .setStatus(status)
            .setMessage(e.getMessage())
            .build();
    }
}