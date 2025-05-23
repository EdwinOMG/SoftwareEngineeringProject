package main.java.server;

import java.util.concurrent.TimeUnit;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import main.grpc.ComputationServiceGrpc;
import main.grpc.ComputationServiceGrpc.ComputationServiceBlockingStub;
import main.grpc.ComputeRequest;
import main.grpc.ComputeResponse;
import main.grpc.Numbers;
import main.java.DataStore;
import main.java.DataStoreImpl;

public class ComputationClient {
    private final ComputationServiceBlockingStub blockingStub;

    public ComputationClient(Channel channel) {

        blockingStub = ComputationServiceGrpc.newBlockingStub(channel);
    }

    public ComputeResponse computeNumbers(Iterable<Integer> numbers, String outputFile, String delimiter) {
    	ComputeRequest request = ComputeRequest.newBuilder()
    			.setNumbers(Numbers.newBuilder().addAllNumbers(numbers).build())
    			.setOutputFile(outputFile)
    			.setDelimiter(delimiter)
    			.build();
    	return blockingStub.compute(request);
    }

    public ComputeResponse computeFromDataStore(String inputFile, String outputFile, String delimiter) {
        DataStore dataStore = new DataStoreImpl();
        Iterable<Integer> numbers = dataStore.readFile(inputFile);
        ComputeResponse response = computeNumbers(numbers, outputFile, delimiter);
        dataStore.writeResults(outputFile, response.getChainsList(), delimiter); // FIX WRITERESULTS
        
        return response;
    }
    
    public static void main(String[] args) throws Exception {
        String target = "localhost:50052";
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {
            ComputationClient client = new ComputationClient(channel);
            ComputeResponse response = client.computeFromDataStore("src/main/java/server/input.text", "src/main/java/server/output.text",",");
            
            System.out.println("Status: " + response);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}