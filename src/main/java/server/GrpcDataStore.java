// File: main.java.grpcclient.GrpcDataStore.java
package main.java.grpcclient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import main.grpc.DataStoreProto.*;
import main.grpc.DataStoreServiceGrpc;
import main.java.*;

import java.util.ArrayList;
import java.util.List;

public class GrpcDataStore implements DataStore {
    private final DataStoreServiceGrpc.DataStoreServiceBlockingStub stub;

    public GrpcDataStore(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.stub = DataStoreServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public Iterable<Integer> read(InputConfig inputConfig) {
        if (!(inputConfig instanceof FileInputConfig)) {
            throw new IllegalArgumentException("Expected FileInputConfig");
        }
        String filePath = ((FileInputConfig) inputConfig).getFilePath();
        ReadRequest request = ReadRequest.newBuilder().setFilePath(filePath).build();
        ReadResponse response = stub.read(request);
        return response.getNumbersList();
    }

    @Override
    public OutputResult appendResult(OutputConfig outputConfig, DigitChains chains, char delimiter) {
        if (!(outputConfig instanceof FileOutputConfig)) {
            throw new IllegalArgumentException("Expected FileOutputConfig");
        }

        String filePath = ((FileOutputConfig) outputConfig).getFilePath();
        List<NumberChain> chainMessages = new ArrayList<>();

        for (Iterable<Integer> chain : chains) {
            NumberChain.Builder builder = NumberChain.newBuilder();
            for (Integer i : chain) builder.addNumbers(i);
            chainMessages.add(builder.build());
        }

        AppendRequest request = AppendRequest.newBuilder()
                .setFilePath(filePath)
                .addAllChains(chainMessages)
                .setDelimiter(String.valueOf(delimiter))
                .build();

        AppendResponse response = stub.append(request);

        return () -> response.getStatus() == AppendResponse.Status.SUCCESS
                ? OutputResult.ShowResultStatus.SUCCESS
                : OutputResult.ShowResultStatus.FAILURE;
    }

    @Override
    public OutputResult writeResults(String filePath, DigitChains chains, String delimiter) {
        throw new UnsupportedOperationException("Use appendResult with FileOutputConfig.");
    }

    @Override
    public Iterable<Integer> read(String filePath) {
        ReadRequest request = ReadRequest.newBuilder().setFilePath(filePath).build();
        ReadResponse response = stub.read(request);
        return response.getNumbersList();
    }
}
