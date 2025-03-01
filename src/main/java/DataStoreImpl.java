package main.java;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataStoreImpl implements DataStore {

    @Override
    public Iterable<Integer> read(InputConfig inputConfig) {
        ArrayList<Integer> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputConfig.getFilePath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Convert each line to an integer and add it to the list
                numbers.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    @Override
    public OutputResult appendResult(OutputConfig outputConfig, Iterable<Integer> result, char delimiter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputConfig.getFilePath(), true))) {
            for (int res : result) {
                writer.write(res + String.valueOf(delimiter));
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return new OutputResult() {
                @Override
                public ShowResultStatus getStatus() {
                    return ShowResultStatus.FAILURE;
                }
            };
        }

        return new OutputResult() {
            @Override
            public ShowResultStatus getStatus() {
                return ShowResultStatus.SUCCESS;
            }
        };
    }
}
