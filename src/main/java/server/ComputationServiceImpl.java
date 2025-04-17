package main.java.server;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import main.grpc.ComputeRequest;
import main.grpc.ComputeResponse;
import main.grpc.NumberChain;
import main.java.ComputeEngine;
import main.java.DataStore;
import main.java.DigitChains;
import main.grpc.ComputationServiceGrpc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComputationServiceImpl extends ComputationServiceGrpc.ComputationServiceImplBase {
    
    private final ComputeEngine computeEngine;
    private final DataStore dataStore;
    
    public ComputationServiceImpl(ComputeEngine computeEngine, DataStore dataStore) {
        this.computeEngine = computeEngine;
        this.dataStore = dataStore;
    }

    @Override
    public void compute(ComputeRequest request, StreamObserver<ComputeResponse> responseObserver) {
        try {
            // 1. Validate request
            if (!request.hasNumbers() && request.getInputFile().isEmpty()) {
                throw new IllegalArgumentException("Either numbers or input file must be provided");
            }

            // 2. Process input
            Iterable<Integer> numbers = request.hasNumbers() 
                ? request.getNumbers().getNumbersList()
                : dataStore.read(request.getInputFile());

            // 3. Execute computation
         // In your service implementation
            DigitChains chains = computeEngine.compute(numbers);

            // Convert to protobuf format
            List<NumberChain> protoChains = new ArrayList<>();
            for (Iterable<Integer> chain : chains) {
                NumberChain.Builder chainBuilder = NumberChain.newBuilder();
                chain.forEach(chainBuilder::addNumbers);
                protoChains.add(chainBuilder.build());
            }

            // Build response
            responseObserver.onNext(ComputeResponse.newBuilder()
                .setStatus(ComputeResponse.Status.SUCCESS)
                .addAllChains(protoChains)
                .build());
            
        } catch (IllegalArgumentException e) {
            responseObserver.onNext(ComputeResponse.newBuilder()
                .setStatus(ComputeResponse.Status.INVALID_INPUT)
                .setMessage(e.getMessage())
                .build());
        } catch (Exception e) {
            responseObserver.onNext(ComputeResponse.newBuilder()
                .setStatus(ComputeResponse.Status.COMPUTATION_ERROR)
                .setMessage("Processing failed: " + e.getMessage())
                .build());
        }
        responseObserver.onCompleted();
    }
}