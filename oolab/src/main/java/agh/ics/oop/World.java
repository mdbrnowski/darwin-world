package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {
    public static void main(String[] args) {
        GrassField map_1 = new GrassField(10);
        RectangularMap map_2 = new RectangularMap(8, 8);
        MapChangeListener observer = new ConsoleMapDisplay();
        map_1.addObserver(observer);
        map_2.addObserver(observer);
        try {
            Simulation simulation_1 = new Simulation(
                    map_1,
                    List.of(new Vector2d(2, 2), new Vector2d(3, 4)),
                    OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r",
                            "f", "f", "f", "f", "f", "f", "f", "f"}));
            Simulation simulation_2 = new Simulation(
                    map_2,
                    List.of(new Vector2d(1, 2), new Vector2d(2, 1)),
                    OptionsParser.parse(new String[]{"l", "f", "f", "f", "f", "f", "l", "l"}));

            SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation_1, simulation_2));
//            simulationEngine.runSync();
            simulationEngine.runAsync();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        System.out.println("The system has completed the operation.");
    }
}
