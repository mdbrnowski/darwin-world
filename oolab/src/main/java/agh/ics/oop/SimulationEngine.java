package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private final List<Thread> simulationThreads = new ArrayList<>();

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runSync() {
        for (Simulation simulation : simulations)
            simulation.run();
    }

    public void runAsync() {
        for (Simulation simulation : simulations) {
            Thread simulationThread = new Thread(simulation);
            simulationThreads.add(simulationThread);
            simulationThread.start();
        }
        awaitSimulationEnd();
    }

    public void awaitSimulationEnd() {
        for (Thread simulationThread : simulationThreads) {
            try {
                simulationThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
