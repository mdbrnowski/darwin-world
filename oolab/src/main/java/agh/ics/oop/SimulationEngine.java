package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private List<Thread> simulationThreads;
    private ExecutorService executorService;

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runSync() {
        for (Simulation simulation : simulations)
            simulation.run();
    }

    public void runAsync() {
        simulationThreads = new ArrayList<>();
        for (Simulation simulation : simulations) {
            Thread simulationThread = new Thread(simulation);
            simulationThreads.add(simulationThread);
            simulationThread.start();
        }
    }

    public void runAsyncInThreadPool() {
        executorService = Executors.newFixedThreadPool(4);
        for (Simulation simulation : simulations) {
            Thread simulationThread = new Thread(simulation);
            executorService.submit(simulationThread);
        }
        executorService.shutdown();
    }

    public void awaitSimulationEnd() throws InterruptedException {
        // for runAsync()
        if (simulationThreads != null) {
            for (Thread simulationThread : simulationThreads)
                simulationThread.join();
        }
        // for runAsyncInThreadPool
        if (executorService != null) {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS))
                executorService.shutdownNow();
        }
    }

    public void shutdown() {
        for (Simulation simulation : simulations) {
            simulation.end();
        }

        for (Thread simulationThread : simulationThreads) {
            try {
                simulationThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
