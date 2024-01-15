package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private List<Thread> simulationThreads;

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runAsync() {
        simulationThreads = new ArrayList<>();
        for (Simulation simulation : simulations) {
            Thread simulationThread = new Thread(simulation);
            simulationThreads.add(simulationThread);
            simulationThread.start();
        }
    }

    public void shutdown() {
        for (Simulation simulation : simulations)
            simulation.end();

        for (Thread simulationThread : simulationThreads) {
            try {
                simulationThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
