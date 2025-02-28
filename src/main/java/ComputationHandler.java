package main.java;

import main.java.project.annotations.NetworkAPI;

@NetworkAPI
public interface ComputationHandler {
    ComputeEngineResult compute(ComputeEngineRequest request);
}