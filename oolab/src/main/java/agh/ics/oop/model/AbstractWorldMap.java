package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.MapVisualizer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.util.*;

public abstract class AbstractWorldMap {
    protected final int width;
    protected final int height;
    protected Multimap<Vector2d, Animal> animals = Multimaps.synchronizedListMultimap(ArrayListMultimap.create());
    protected final Map<Vector2d, Grass> plants = new HashMap<>();
    protected final List<Animal> deadAnimals = new ArrayList<>();
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

    public synchronized void place(Animal animal) {
        animals.put(animal.getPosition(), animal);
    }

    public synchronized void move(Animal animal) {
        Vector2d oldPosition = animal.getPosition();
        animal.move(animal.getPosition().add(animal.getOrientation().toUnitVector()));
        animals.remove(oldPosition, animal);
        animals.put(animal.getPosition(), animal);
    }

    public List<Animal> getAnimalsAt(Vector2d position) {
        return new ArrayList<>(animals.get(position));
    }

    public List<Animal> getAnimals() {
        return new ArrayList<>(animals.values());
    }

    public int getDay() {
        return day;
    }

    public List<Animal> getDeadAnimals() {
        return deadAnimals;
    }

    public Set<Vector2d> getRecentlyDead() {
        return recentlyDead;
    }

    public Grass getPlantAt(Vector2d position) {
        return plants.get(position);
    }

    public List<Grass> getPlants() {
        return new ArrayList<>(plants.values());
    }

    public void addPlants(Map<Vector2d, Grass> plants) {
        this.plants.putAll(plants);
    }

    public void removePlant(Grass plant) {
        plants.remove(plant.getPosition());
    }

    public Boundary getCurrentBounds() {
        return new Boundary(new Vector2d(0, 0), new Vector2d(width, height));
    }

    public UUID getId() {
        return id;
    }

    public void mapChanged(String message) {
        for (MapChangeListener listener : listeners)
            listener.mapChanged(this, message);
    }

    public synchronized void removeDead() {
        recentlyDead = new HashSet<>(Multimaps.filterEntries(animals,
                e -> e.getValue().getEnergy() == 0).keySet());
        deadAnimals.addAll(Multimaps.filterValues(animals,
                e -> e.getEnergy() == 0).values().stream().toList());

        List<Animal> recDeadAnimals = getAnimals().stream().filter(e -> e.getEnergy() <= 0).toList();
        for (Animal animal : recDeadAnimals)
            animal.setDiedOn(day);

        animals = Multimaps.synchronizedMultimap(ArrayListMultimap.create(Multimaps.filterEntries(animals,
                e -> e.getValue().getEnergy() > 0)));
    }

    public int getNumberOfEmptyFields() {
        int result = 0;
        for (int i = 0; i <= width; i++)
            for (int j = 0; j <= height; j++)
                if (getAnimalsAt(new Vector2d(i, j)).isEmpty())
                    result++;
        return result;
    }

    @Override
    public String toString() {
        Boundary b = getCurrentBounds();
        return new MapVisualizer(this).draw(b.bottomLeft(), b.topRight());
    }

    public void addObserver(MapChangeListener listener) {
        listeners.add(listener);
    }

    public abstract Vector2d getNextPosition(Vector2d position, Vector2d move);
}
