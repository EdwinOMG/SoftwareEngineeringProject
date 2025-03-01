package main.java;


import java.io.BufferedReader;
import java.io.BufferedWriter;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
import java.io.File;
>>>>>>> Stashed changes
=======
import java.io.File;
>>>>>>> Stashed changes
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataStoreImpl implements DataStore {

    @Override
    public Iterable<Integer> read(InputConfig inputConfig) {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
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
=======
=======
>>>>>>> Stashed changes
        List<Integer> inputData = new ArrayList<>();
        
        // Assume the file path is passed as input (e.g., through the InputConfig).
        File inputFile = new File(inputConfig.getInput().toString());

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse each line as an integer and add it to the list
                inputData.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle error as appropriate
        }

        return inputData;
    }

    @Override
    public OutputResult appendResult(OutputConfig outputConfig, DigitChains chains, char delimiter) {
        // Assume outputConfig has a file path for writing output
        File outputFile = new File(outputConfig.toString());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // For each chain, write each number followed by the delimiter to the output file
            for (Iterable<Integer> chain : chains) {
                for (Integer number : chain) {
                    writer.write(number.toString());
                    writer.write(delimiter); // Write delimiter after each number
                }
                writer.newLine(); // Move to the next line for the next chain
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle error as appropriate
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
        }

        // Return success status after writing to the file
        return new OutputResult() {
            @Override
            public ShowResultStatus getStatus() {
                return ShowResultStatus.SUCCESS;
            }
        };
    }
}
