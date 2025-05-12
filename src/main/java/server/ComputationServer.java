package main.java.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import io.grpc.*;
import io.grpc.protobuf.services.ProtoReflectionService;
import main.java.*;
import main.grpc.*;

public class ComputationServer {
    private Server server;
    private ManagedChannel dataStoreChannel;

    private void start() throws IOException {
        int port = 50052;
        
        // Create channel to DataStoreServer
        dataStoreChannel = ManagedChannelBuilder.forAddress("localhost", 50053)
                .usePlaintext()
                .build();
        
        // Create gRPC stub
        DataStoreServiceGrpc.DataStoreServiceBlockingStub dataStoreStub = 
                DataStoreServiceGrpc.newBlockingStub(dataStoreChannel);
        
        // Create DataStoreClient implementation
        DataStoreClient dataStoreClient = new DataStoreClient(dataStoreChannel) {
            @Override
            public Iterable<Integer> readFile(String path) {
                return dataStoreStub.read(
                    ReadRequest.newBuilder().setFilePath(path).build()
                ).getNumbersList();
            }
            
            @Override
            public void writeFile(String path, Iterable<NumberChain> chains, String delimiter) {
                dataStoreStub.append(
                    AppendRequest.newBuilder()
                        .setFilePath(path)
                        .addAllChains(chains)
                        .setDelimiter(delimiter)
                        .build()
                );
            }
        };
        
        // Create handler
        ComputationHandler handler = new ComputationHandlerImpl(
                new ComputeEngineImpl(),
                dataStoreClient
        );
        
        // Start server
        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new ComputationServiceImpl(handler))
                .addService(ProtoReflectionService.newInstance())
                .build()
                .start();
        
        System.out.println("Server started on port " + port);
        
        // Shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** Shutting down gRPC server");
            try {
                if (server != null) {
                    server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                }
                if (dataStoreChannel != null) {
                    dataStoreChannel.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** Server shut down");
        }));
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        ComputationServer server = new ComputationServer();
        server.start();
        server.blockUntilShutdown();
    }
}