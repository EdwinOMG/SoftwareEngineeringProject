// File: main.java.grpcserver.DataStoreServer.java
package main.java.grpcserver;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class DataStoreServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50052)
                .addService(new DataStoreServiceImpl())
                .build()
                .start();
        System.out.println("DataStoreService running on port 50052");
        server.awaitTermination();
    }
}
