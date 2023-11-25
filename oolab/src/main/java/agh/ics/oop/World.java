package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        MapChangeListener observer = new ConsoleMapDisplay();
        List<AbstractWorldMap> maps = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            GrassField map = new GrassField(10);
            map.addObserver(observer);
            maps.add(map);
        }
        try {
            List<Simulation> simulations = new ArrayList<>();
            for (AbstractWorldMap map : maps) {
                Simulation simulation = new Simulation(
                        map,
                        List.of(new Vector2d(2, 2), new Vector2d(3, 4)),
                        OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f",
                                "f", "f", "f", "f"})
                );
                simulations.add(simulation);
            }
            SimulationEngine simulationEngine = new SimulationEngine(simulations);
            simulationEngine.runAsync();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        System.out.println("The system has completed the operation.");
    }
}
