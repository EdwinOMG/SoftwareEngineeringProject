package main.java;

public class ComputationHandlerUtil {

    public static ComputeEngineResult processRequest(ComputeEngine computeEngine, DataStore dataStore, ComputeEngineRequest request) {
        if (request == null || request.getInput() == null || request.getOutput() == null) {
            return ComputeEngineResult.FAIL;
        }

        try {
            Iterable<Integer> inputData = dataStore.read(request.getInput());
            if (inputData == null) {
                return ComputeEngineResult.FAIL;
            }

            DigitChains chains = computeEngine.compute(inputData);
            if (chains == null) {
                return ComputeEngineResult.FAIL;
            }

            OutputResult outputResult = dataStore.appendResult(request.getOutput(), chains, request.getDelimiter());
            return outputResult.getStatus() == OutputResult.ShowResultStatus.SUCCESS
                    ? ComputeEngineResult.SUCCESS
                    : ComputeEngineResult.FAIL;
        } catch (Exception e) {
            return ComputeEngineResult.FAIL;
        }
    }
}
