package agh.ics.oop.model;

import agh.ics.oop.model.util.PositionAlreadyOccupiedException;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap<Animal, Vector2d> {
    protected Map<Vector2d, Animal> animals = new HashMap<>();

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public void place(Animal animal) throws PositionAlreadyOccupiedException {
        if (canMoveTo(animal.getPosition()))
            animals.put(animal.getPosition(), animal);
        else
            throw new PositionAlreadyOccupiedException(animal.getPosition());
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

    @Override
    public Collection<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }
}
