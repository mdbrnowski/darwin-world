package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.MapVisualizer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected final int width;
    protected final int height;
    protected Multimap<Vector2d, Animal> animals = ArrayListMultimap.create();
    protected Map<Vector2d, Grass> plants = new HashMap<>();
    protected Set<Vector2d> recentlyDead = new HashSet<>();
    private final List<MapChangeListener> listeners = new ArrayList<>();
    private final UUID id;
    protected int day = 0;

    public AbstractWorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        id = UUID.randomUUID();
    }

    public void nextDay() {
        day += 1;
    }

    public abstract void setPlants(Map<Vector2d, Grass> plants);

    @Override
    public void place(Animal animal) {
        animals.put(animal.getPosition(), animal);
        mapChanged("Added a new animal at %s".formatted(animal.getPosition()));
    }

    @Override
    public void move(Animal animal) {
        Vector2d oldPosition = animal.getPosition();
        animal.move(animal.getPosition().add(animal.getOrientation().toUnitVector()));
        animals.remove(oldPosition, animal);
        animals.put(animal.getPosition(), animal);
        mapChanged("Moved an animal to %s".formatted(animal.getPosition()));
    }

    @Override
    public List<Animal> animalsAt(Vector2d position) {
        return new ArrayList<>(animals.get(position));
    }

    @Override
    public List<Animal> getAnimals() {
        return new ArrayList<>(animals.values());
    }

    @Override
    public Grass plantAt(Vector2d position) {
        return plants.get(position);
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(new Vector2d(0, 0), new Vector2d(width, height));
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

    @Override
    public void removeDead() {
        recentlyDead = Multimaps.filterEntries(animals, e -> e.getValue().getEnergy() == 0).keySet();
        animals = Multimaps.filterEntries(animals, e -> e.getValue().getEnergy() > 0);
        mapChanged("Dead animals removed");
    }

    @Override
    public String toString() {
        Boundary b = getCurrentBounds();
        return new MapVisualizer(this).draw(b.bottomLeft(), b.topRight());
    }

    public void addObserver(MapChangeListener listener) {
        listeners.add(listener);
    }

    public Set<Vector2d> getRecentlyDead() {
        return recentlyDead;
    }

    public abstract Vector2d getNextPosition(Vector2d position, Vector2d move);

}
