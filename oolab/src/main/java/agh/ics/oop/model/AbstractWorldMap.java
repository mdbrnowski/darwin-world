package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.MapVisualizer;
import agh.ics.oop.model.util.PositionAlreadyOccupiedException;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements WorldMap {
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected List<Vector2d> recentlyDead = new ArrayList<>();
    private final List<MapChangeListener> listeners = new ArrayList<>();
    private AbstractVegetation vegetator;
    private final UUID id;

    public AbstractWorldMap() {
        id = UUID.randomUUID();
    }

    /*
     * returns a list of positions of recently dead animals
     */
    public List<Vector2d> getRecentlyDead() {
        return recentlyDead;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public void place(Animal animal) throws PositionAlreadyOccupiedException {
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            mapChanged("Added a new animal at %s".formatted(animal.getPosition()));
        } else
            throw new PositionAlreadyOccupiedException(animal.getPosition());
    }


    @Override
    public void move(Animal animal) {
        Vector2d old_position = animal.getPosition();
        animal.move(this, animal.getPosition().add(animal.getOrientation().toUnitVector()));
        if (!animal.isAt(old_position)) {
            animals.remove(old_position);
            animals.put(animal.getPosition(), animal);
            mapChanged("Moved an animal to %s".formatted(animal.getPosition()));
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

    @Override
    public String toString() {
        Boundary b = getCurrentBounds();
        return new MapVisualizer(this).draw(b.bottomLeft(), b.topRight());
    }

    @Override
    public UUID getId() {
        return id;
    }

    public Map<Vector2d, Animal> getAnimals() {
        return animals;
    }

    protected void mapChanged(String message) {
        for (MapChangeListener listener : listeners) {
            listener.mapChanged(this, message);
        }
    }

    public void addObserver(MapChangeListener listener) {
        listeners.add(listener);
    }

    public void removeObserver(MapChangeListener listener) {
        listeners.remove(listener);
    }

    /*
     * removes dead animals from animals hashmap and actualizes the hashmap of recentlyDead animals
     */
    public void removeDead() {

        recentlyDead = animals.entrySet().stream()
                .filter(entry -> entry.getValue().getEnergy() == 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        animals = animals.entrySet().stream()
                .filter(entry -> entry.getValue().getEnergy() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public abstract void vegetate();

    public abstract Vector2d getNextPosition(Vector2d position, Vector2d move);

}
