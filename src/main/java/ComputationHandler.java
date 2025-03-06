package main.java;

import main.java.project.annotations.NetworkAPI;
// returns whether the computation passed or failed
@NetworkAPI
public interface ComputationHandler {
    ComputeEngineResult compute(ComputeEngineRequest request);
}