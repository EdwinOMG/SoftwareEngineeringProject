package main.java.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.protobuf.services.ProtoReflectionService;
import main.grpc.AppendRequest;
import main.grpc.DataStoreServiceGrpc;
import main.java.ComputationHandlerImpl;
import main.java.ComputeEngineImpl;

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
        DataStoreClient dataStoreClient = new DataStoreClient(dataStoreChannel);
        
        // Create handler
        ComputationHandlerImpl handler = new ComputationHandlerImpl(
                new ComputeEngineImpl(),
                dataStoreClient
        );
        
        // Start server
        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new ComputationServiceImpl(handler, dataStoreClient))
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