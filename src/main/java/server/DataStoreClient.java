package main.java.server;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import main.grpc.AppendRequest;
import main.grpc.AppendResponse;
import main.grpc.DataStoreServiceGrpc;
import main.grpc.DataStoreServiceGrpc.DataStoreServiceBlockingStub;
import main.grpc.NumberChain;
import main.grpc.ReadRequest;
import main.grpc.ReadResponse;

public class DataStoreClient {
	private final DataStoreServiceGrpc.DataStoreServiceBlockingStub stub;
    public DataStoreClient(ManagedChannel channel) {
        this.stub = DataStoreServiceGrpc.newBlockingStub(channel);
    }
    
    public Iterable<Integer> readFile(String filePath) throws Exception {
    	try {
    		ReadRequest request = ReadRequest.newBuilder()
    				.setFilePath(filePath)
    				.build();
    		ReadResponse response = stub.read(request);
    		
    		if(!response.getError().isEmpty()) {
    			System.err.println("Read failed: " + response.getError());
    			return List.of();
    		}
    		return response.getNumbersList();
    	} catch (Exception e) {
    		System.err.println("Failed: " + e);
    		return List.of();
    	}
    }

    public void writeFile(String filePath, Iterable<NumberChain> chains, String string) throws IOException {
    	AppendRequest request = AppendRequest.newBuilder()
    			.setFilePath(filePath)
    			.addAllChains(chains)
    			.setDelimiter(string)
    			.build();
    	
    		stub.append(request);
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
            Iterable<Integer> numbers = client.readFile("src/main/java/server/input.text");
            
            System.out.println("Read numbers: " + numbers);
            
            client.writeFile("src/main/java/server/output.text", List.of(
            		NumberChain.newBuilder().addAllNumbers(List.of(1,2,3)).build(), 
            		NumberChain.newBuilder().addAllNumbers(List.of(4, 5, 6)).build()
            		),
            		","
            		);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
