package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.PositionAlreadyOccupiedException;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final WorldMap map;
    private long sleepTime = 0;
    private AbstractVegetation vegetation;

    public Simulation(WorldMap map, List<Vector2d> positions) {
        this.map = map;
        this.vegetation = new ForestEquators(0, 10, 0, 10, 15);
//        this.vegetation = new LifeGivingCorpses(0, 10, 0, 10, 15);
        for (Vector2d position : positions) {
            Animal a = new Animal(position, MapDirection.NORTH, new FullPredestinationGenome(List.of(1, 2)));
            a.setEnergy(2);
            try {
                map.place(a);
            } catch (PositionAlreadyOccupiedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        map.mapChanged("All animals placed");
    }

    public Simulation(WorldMap map, List<Vector2d> positions, long sleepTime) {
        this(map, positions);
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        sleep();
        while (!map.getAnimals().isEmpty()) {
            map.removeDead();
            var animals = map.getAnimals();
            for (Animal animal : animals) {
                animal.decrementEnergy();
                map.move(animal);  // todo: should move and change direction
            }
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
