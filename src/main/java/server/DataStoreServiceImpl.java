package main.java.server;


import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import main.grpc.AppendRequest;
import main.grpc.AppendResponse;
import main.grpc.DataStoreProto.*;
import main.grpc.DataStoreServiceGrpc;
import main.grpc.ReadRequest;
import main.grpc.ReadResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
public class DataStoreServiceImpl extends DataStoreServiceGrpc.DataStoreServiceImplBase {
    
	@Override
	public void read(ReadRequest request, StreamObserver<ReadResponse> responseObserver) {
	    try {
	        List<String> lines = Files.readAllLines(Path.of(request.getFilePath()));
	        List<Integer> numbers = lines.stream()
	            .flatMap(line -> Arrays.stream(line.split(",")))
	            .map(String::trim)
	            .map(Integer::parseInt)
	            .collect(Collectors.toList());

	        ReadResponse response = ReadResponse.newBuilder()
	            .addAllNumbers(numbers)
	            .build();

	        responseObserver.onNext(response);
	        responseObserver.onCompleted();
	    } catch (IOException e) {
	        responseObserver.onError(Status.INVALID_ARGUMENT
	            .withDescription("File read error: " + e.getMessage())
	            .asRuntimeException());
	    }
	}
    @Override
    public void append(AppendRequest request, StreamObserver<AppendResponse> responseObserver) {
        try {
            String content = request.getChainsList().stream()
                .map(chain -> chain.getNumbersList().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(request.getDelimiter())))
                .collect(Collectors.joining("\n"));
            
            Files.writeString(
                Path.of(request.getFilePath()),
                content + "\n",
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
            
            responseObserver.onNext(AppendResponse.newBuilder()
                .setStatus(AppendResponse.Status.SUCCESS)
                .build());
            responseObserver.onCompleted();
        } catch (IOException e) {
            responseObserver.onNext(AppendResponse.newBuilder()
                .setStatus(AppendResponse.Status.FAILURE)
                .setError("File write error: " + e.getMessage())
                .build());
            responseObserver.onCompleted();
        }
    }
}