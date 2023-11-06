package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap implements WorldMap<Animal, Vector2d> {
    private final int width;
    private final int height;
    private final Map<Vector2d, Animal> animals;

    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.animals = new HashMap<>();
    }

    public boolean canMoveTo(Vector2d position) {
        return position.follows(new Vector2d(0, 0)) && position.precedes(new Vector2d(width, height))
                && !isOccupied(position);
    }

    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;
    }

    public void move(Animal animal, MoveDirection direction) {
        Vector2d old_position = animal.getPosition();
        animal.move(direction, this);
        if (!animal.isAt(old_position)) {
            animals.remove(old_position);
            animals.put(animal.getPosition(), animal);
        }
    }

    public boolean isOccupied(Vector2d position) {
        return animals.get(position) != null;
    }

    public Animal objectAt(Vector2d position) {
        return animals.get(position);
    }

    public String toString() {
        return new MapVisualizer<>(this).draw(new Vector2d(0, 0), new Vector2d(width, height));
    }
}
