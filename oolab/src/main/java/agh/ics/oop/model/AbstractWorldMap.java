package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap<Animal, Vector2d> {
    protected Map<Vector2d, Animal> animals = new HashMap<>();

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d old_position = animal.getPosition();
        animal.move(direction, this);
        if (!animal.isAt(old_position)) {
            animals.remove(old_position);
            animals.put(animal.getPosition(), animal);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.get(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }
}
