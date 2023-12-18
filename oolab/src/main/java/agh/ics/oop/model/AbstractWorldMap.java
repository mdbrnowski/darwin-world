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
    private final UUID id;
    /*
     * Determines the day of the maps' life
     */
    protected int day=0;

    public AbstractWorldMap() {
        id = UUID.randomUUID();
    }
    public void nextDay(){
        day+=1;
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
    public List<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }

    @Override
    public List<Animal> getAnimals() {
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

    @Override
    public void mapChanged(String message) {
        for (MapChangeListener listener : listeners) {
            listener.mapChanged(this, message);
        }
        System.out.println("Listener: " + message);  // todo: it's only for debug
    }

    public void addObserver(MapChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeDead() {
        recentlyDead = animals.entrySet().stream()
                .filter(entry -> entry.getValue().getEnergy() == 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        animals = animals.entrySet().stream()
                .filter(entry -> entry.getValue().getEnergy() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        mapChanged("Dead animals removed");
    }

    public List<Vector2d> getRecentlyDead() {
        return recentlyDead;
    }

    public abstract Vector2d getNextPosition(Vector2d position, Vector2d move);

}
