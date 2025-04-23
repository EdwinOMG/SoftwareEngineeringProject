package main.java.server;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.File;
import com.google.rpc.context.AttributeContext.Request;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import main.grpc.AppendRequest;
import main.grpc.AppendResponse;
import main.grpc.ComputeRequest;
import main.grpc.DataStoreServiceGrpc;
import main.grpc.DataStoreServiceGrpc.DataStoreServiceBlockingStub;
import main.grpc.NumberChain;
import main.grpc.ReadRequest;
import main.grpc.ReadResponse;


public class DataStoreClient { // Boilerplate TODO: change to <servicename>Client
    private final DataStoreServiceBlockingStub blockingStub; // Boilerplate TODO: update to appropriate blocking stub

    public DataStoreClient(Channel channel) {
        blockingStub = DataStoreServiceGrpc.newBlockingStub(channel);  // Boilerplate TODO: update to appropriate blocking stub
    }

    // Boilerplate TODO: replace this method with actual client call/response logic
    public List<Integer> readFile(String filePath){
    	try {
    		ReadRequest request = ReadRequest.newBuilder()
    				.setFilePath(filePath)
    				.build();
    		ReadResponse response = blockingStub.read(request);
    		
    		if(!response.getError().isEmpty()) {
    			System.err.println("Read failed: " + response.getError());
    			return List.of(); // return our empty list since there was an error
    		}
    		return response.getNumbersList();
    	} catch (StatusRuntimeException e) {
    		System.err.println("Failed: " + e.getStatus());
    		return List.of();
    	}
    }
    public boolean appendChains(String filePath, List<NumberChain> chains, String delimiter) {
    	try {
    		AppendRequest.Builder requestBuilder = AppendRequest.newBuilder()
    				.setFilePath(filePath)
    				.setDelimiter(delimiter);
    		
    		chains.forEach(chain ->
    			requestBuilder.addChains(
    					NumberChain.newBuilder()
    					.addAllNumbers(chain.getNumbersList())
    					.build()
    					)
    				);
    		
    		AppendResponse response = blockingStub.append(requestBuilder.build());
    		
    		if (response.getStatus() == AppendResponse.Status.FAILURE) {
    			System.err.println("Append failed: " + response.getError());
    			
    			return false;
    		}
    		return true;
    	}  catch (StatusRuntimeException e) {
    		System.err.println("RPC failed: " + e.getStatus());
    		return false;
    	}
    }

    public static void main(String[] args) throws Exception {
        String target = "localhost:50050";  // Boilerplate TODO: make sure the server/port match the server/port you want to connect to

        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {
        	File file = new File("src/main/java/server/input.text"); // LITTLE TEST TO FIND THE BUILD PATH IM DEAD
        	
            DataStoreClient client = new DataStoreClient(channel); // Boilerplate TODO: update to this class name
            if(file.isFile()) {
            	System.out.println("ITS A FILE");
            }
            else {
            	System.out.println("ITS NOT A FILE");
            }
            List<Integer> numbers = client.readFile("src/main/java/server/input.text");
            
            System.out.println("Read numbers: " + numbers);
            boolean success = client.appendChains("src/main/java/server/output.text", List.of(
            		NumberChain.newBuilder().addAllNumbers(List.of(1, 2, 3)).build(), 
            		NumberChain.newBuilder().addAllNumbers(List.of(4, 5, 6)).build()
            		),
            		","
            		);
            
            System.out.println("Appended Successfully!");
        } finally {
        	
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
