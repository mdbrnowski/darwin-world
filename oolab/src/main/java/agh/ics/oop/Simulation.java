package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<MoveDirection> directions;
    private final List<Animal> animals = new ArrayList<>();
    public Simulation(List<MoveDirection> directions, List<Vector2d> positions) {
        this.directions = directions;
        for (Vector2d position : positions) {
            animals.add(new Animal(position));
        }
    }

    public void run() {
        int n = animals.size();
        for (int i = 0; i < directions.size(); i++) {
            animals.get(i % n).move(directions.get(i));
            System.out.printf("Zwierzę %d : %s%n", i % n, animals.get(i % n));
        }
    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
