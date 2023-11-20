package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.PositionAlreadyOccupiedException;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final WorldMap map;
    private final List<MoveDirection> directions;
    private final List<Animal> animals;
    public Simulation(WorldMap map, List<Vector2d> positions, List<MoveDirection> directions) {
        this.map = map;
        this.directions = directions;
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

    public void run() {
        int n = animals.size();
        for (int i = 0; i < directions.size(); i++)
            map.move(animals.get(i % n), directions.get(i));
    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
