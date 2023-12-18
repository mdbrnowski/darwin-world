package agh.ics.oop;

import agh.ics.oop.model.*;
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
            Animal a = new Animal(position, MapDirection.NORTH, new FullPredestinationGenome(List.of(1, 2)));
            a.setEnergy(2);
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
        // todo: change this
        map.removeDead();
        for (int i = 0; i < 10; i++) {
            map.move(map.getAnimals().get(0));
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
