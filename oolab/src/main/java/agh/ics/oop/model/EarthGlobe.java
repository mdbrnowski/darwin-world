package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

import java.util.*;

public class EarthGlobe extends AbstractWorldMap {

    private final int width;
    private final int height;
    private Map<Vector2d, Grass> plants = new HashMap<>();


    public EarthGlobe(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> result = new ArrayList<>(animals.values());
        result.addAll(plants.values());
        return result;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (super.isOccupied(position)) return animals.get(position);
        return plants.get(position);
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

        animal.move(this, newPosition);
        if (!animal.isAt(oldPosition)) {
            animals.remove(oldPosition);
            animals.put(animal.getPosition(), animal);
            mapChanged("Moved an animal to %s".formatted(animal.getPosition()));
        }
    }

    @Override
    public void setPlants(Map<Vector2d, Grass> plants) {
        this.plants = plants;
        System.out.println(plants);
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(new Vector2d(0, 0), new Vector2d(width, height));
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
