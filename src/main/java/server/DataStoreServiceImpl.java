package src.main.java.server;

public class DataStoreServiceImpl {

}// File: main.java.grpcserver.DataStoreServiceImpl.java
package main.java.grpcserver;

import io.grpc.stub.StreamObserver;
import main.grpc.DataStoreServiceGrpc;
import main.grpc.DataStoreProto.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataStoreServiceImpl extends DataStoreServiceGrpc.DataStoreServiceImplBase {

    @Override
    public void read(ReadRequest request, StreamObserver<ReadResponse> responseObserver) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(request.getFilePath()));
            List<Integer> numbers = new ArrayList<>();

            for (String line : lines) {
                for (String part : line.split("[,\\s;]+")) {
                    if (!part.isBlank()) {
                        numbers.add(Integer.parseInt(part.trim()));
                    }
                }
            }

            ReadResponse.Builder response = ReadResponse.newBuilder();
            response.addAllNumbers(numbers);
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onNext(
                ReadResponse.newBuilder().setError("Failed: " + e.getMessage()).build()
            );
            responseObserver.onCompleted();
        }
    }

    @Override
    public void append(AppendRequest request, StreamObserver<AppendResponse> responseObserver) {
        try {
            List<String> lines = new ArrayList<>();
            for (var chain : request.getChainsList()) {
                lines.add(String.join(request.getDelimiter(), 
                    chain.getNumbersList().stream().map(Object::toString).toList()));
            }

            Files.write(Paths.get(request.getFilePath()), lines);

            responseObserver.onNext(
                AppendResponse.newBuilder().setStatus(AppendResponse.Status.SUCCESS).build()
            );
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onNext(
                AppendResponse.newBuilder()
                        .setStatus(AppendResponse.Status.FAILURE)
                        .setError(e.getMessage())
                        .build()
            );
            responseObserver.onCompleted();
        }
    }
}

