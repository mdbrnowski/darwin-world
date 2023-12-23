package agh.ics.oop.model;

import java.util.*;

public class EarthGlobe extends AbstractWorldMap {

    public EarthGlobe(int width, int height) {
        super(width, height);
    }

    @Override
    public void move(Animal animal) {
        Vector2d oldPosition = animal.getPosition();

        MapDirection newOrientation = animal.getOrientation().add(animal.getGenome().iterate(animal.getAge()));
        animal.setOrientation(newOrientation);

        Vector2d newCandidate = animal.getPosition().add(animal.getOrientation().toUnitVector());

        Vector2d newPosition;

        if (newCandidate.getY() > height || newCandidate.getY() < 0) {
            animal.setOrientation(animal.getOrientation().reverse());
            newPosition = new Vector2d((newCandidate.getX() + width + 1) % (width + 1), oldPosition.getY());
        } else {
            newPosition = new Vector2d((newCandidate.getX() + width + 1) % (width + 1), newCandidate.getY());
        }

        animal.move(newPosition);
        animals.remove(oldPosition, animal);
        animals.put(animal.getPosition(), animal);
        mapChanged("Moved an animal to %s".formatted(animal.getPosition()));
    }

    @Override
    public void setPlants(Map<Vector2d, Grass> plants) {
        this.plants = plants;
        System.out.println(plants);
    }

    @Override
    public Vector2d getNextPosition(Vector2d position, Vector2d move) {
        Vector2d newCandidate = position.add(move);
        Vector2d newPosition;
        if (newCandidate.getY() > height || newCandidate.getY() < 0) {
            newPosition = new Vector2d((newCandidate.getX() + width + 1) % (width + 1), position.getY());
        } else {
            newPosition = new Vector2d((newCandidate.getX() + width + 1) % (width + 1), newCandidate.getY());
        }

        return newPosition;
    }
}
