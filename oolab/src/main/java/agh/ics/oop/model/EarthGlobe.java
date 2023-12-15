package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EarthGlobe extends AbstractWorldMap {
 
    private final int width;
    private final int height;
    private AbstractVegetation vegetator;
    private Map<Vector2d, Grass> plants = new HashMap<Vector2d, Grass>();



    public EarthGlobe(int width, int height, AbstractVegetation vegetator) {
        super();
        this.width = width;
        this.height = height;
        this.vegetator = vegetator;

        plants = vegetator.vegatate(this);
    }

    @Override
    public Collection<WorldElement> getElements() {
        Collection<WorldElement> result = new ArrayList<>(animals.values());
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
        Vector2d old_position = animal.getPosition();

        Vector2d newCandidate = animal.getPosition().add(animal.getOrientation().toUnitVector());

        Vector2d newPosition;

        if (newCandidate.getY() > height || newCandidate.getY() < 0) {
            animal.setOrientation(animal.getOrientation().reverse());
            newPosition = new Vector2d((newCandidate.getX() + width + 1) % (width + 1), old_position.getY());
        } else {
            newPosition = new Vector2d((newCandidate.getX() + width + 1) % (width + 1), newCandidate.getY());
        }

        animal.move(this, newPosition);
        if (!animal.isAt(old_position)) {
            animals.remove(old_position);
            animals.put(animal.getPosition(), animal);
            mapChanged("Moved an animal to %s".formatted(animal.getPosition()));
        }
    }

    /*
     * Places plants on the map using attribute vegetator
     */
    public void vegetate() {
        plants.putAll(vegetator.vegatate(this));
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(new Vector2d(0, 0), new Vector2d(width, height));
    }

    @Override
    public Vector2d getNextPosition(Vector2d position, Vector2d move){
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
