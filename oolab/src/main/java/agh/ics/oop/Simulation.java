package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.PositionAlreadyOccupiedException;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final List<Animal> animals;
    private long sleepTime = 0;
    public Simulation(WorldMap map, List<Vector2d> positions) {
        this.map = map;
        this.animals = new ArrayList<>();
        for (Vector2d position : positions) {
            Animal a = new Animal(position);
            try {
                map.place(a);
                animals.add(a);
            } catch (PositionAlreadyOccupiedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public Simulation(WorldMap map, List<Vector2d> positions, long sleepTime) {
        this(map, positions);
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        int n = animals.size();
        // todo
    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
