package main.java.server;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.File;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import main.grpc.AppendRequest;
import main.grpc.AppendResponse;
import main.grpc.DataStoreServiceGrpc;
import main.grpc.DataStoreServiceGrpc.DataStoreServiceBlockingStub;
import main.grpc.NumberChain;
import main.grpc.ReadRequest;
import main.grpc.ReadResponse;

public class DataStoreClient {
    private final DataStoreServiceBlockingStub blockingStub;

    public DataStoreClient(Channel channel) {
        blockingStub = DataStoreServiceGrpc.newBlockingStub(channel);
    }

    public List<Integer> readFile(String filePath){
    	try {
    		ReadRequest request = ReadRequest.newBuilder()
    				.setFilePath(filePath)
    				.build();
    		ReadResponse response = blockingStub.read(request);
    		
    		if(!response.getError().isEmpty()) {
    			System.err.println("Read failed: " + response.getError());
    			return List.of();
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
        String target = "localhost:50050";

        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {
        	File file = new File("src/main/java/server/input.text");
        	
            DataStoreClient client = new DataStoreClient(channel);
            if(file.isFile()) {
            	System.out.println("ITS A FILE");
            } else {
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
            System.out.println(success);
            
            System.out.println("Appended Successfully!");
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
