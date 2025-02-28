package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataStoreImpl implements DataStore {
    
    @Override
    public Iterable<Integer> read(InputConfig inputConfig) {
        String filePath = inputConfig.getFilePath();
        ArrayList<Integer> numbers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                numbers.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numbers;
    }

    @Override
    public OutputResult appendResult(OutputConfig output, Iterable<Integer> result, char delimiter) {
        // In this implementation, we're simply returning success.
        // You could extend this to write the results to a file or other output destination.
        return new OutputResultImpl(ShowResultStatus.SUCCESS);
    }
}
