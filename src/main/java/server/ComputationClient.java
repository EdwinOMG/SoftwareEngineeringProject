package main.java.server;



import java.util.List;
import java.util.concurrent.TimeUnit;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import main.grpc.ComputationServiceGrpc;
import main.grpc.ComputationServiceGrpc.ComputationServiceBlockingStub;
import main.grpc.ComputeRequest;
import main.grpc.ComputeResponse;
import main.grpc.Numbers;
import main.java.DataStore;
import main.java.DataStoreImpl;



public class ComputationClient { // Boilerplate TODO: change to <servicename>Client
    private final ComputationServiceBlockingStub blockingStub; // Boilerplate TODO: update to appropriate blocking stub

    public ComputationClient(Channel channel) {
        blockingStub = ComputationServiceGrpc.newBlockingStub(channel);  // Boilerplate TODO: update to appropriate blocking stub
    }

    // Boilerplate TODO: replace this method with actual client call/response logic
    
    public ComputeResponse computeNumbers(Iterable<Integer> numbers, String outputFile, String delimiter) {

    public ComputeResponse computeNumbers(List<Integer> numbers, String outputFile, String delimiter) {
    	ComputeRequest request = ComputeRequest.newBuilder()
    			.setNumbers(Numbers.newBuilder().addAllNumbers(numbers).build())
    			.setOutputFile(outputFile)
    			.setDelimiter(delimiter)
    			.build();
    	return blockingStub.compute(request);
    }

    
    public void computeFromDataStore(String inputFile, String outputFile, String delimiter) {
        DataStore dataStore = new DataStoreImpl();

        Iterable<Integer> numbers = dataStore.read(inputFile); // Reuse DataStore
        computeNumbers(numbers, outputFile, delimiter);
    }
    
    public static void main(String[] args) throws Exception {
        String target = "localhost:50052";  // Boilerplate TODO: make sure the server/port match the server/port you want to connect to
        DataStore dataStore = new DataStoreImpl();

    public static void main(String[] args) throws Exception {
        String target = "localhost:50052";  // Boilerplate TODO: make sure the server/port match the server/port you want to connect to

 
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {
            ComputationClient client = new ComputationClient(channel); // Boilerplate TODO: update to this class name
            Iterable<Integer> numbers = dataStore.read("src/main/java/server/input.text");
            ComputeResponse response = client.computeNumbers(
            		numbers,
            ComputeResponse response = client.computeNumbers(
            		List.of(44,32,15),
            		"src/main/java/server/output.text",
            		","
            		);
            
            System.out.println("Status: " + response);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}