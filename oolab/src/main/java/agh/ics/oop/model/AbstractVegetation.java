package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;

import static java.lang.Math.*;

public abstract class AbstractVegetation {
    protected Set<Vector2d> preferredFields = new HashSet<>();
    protected Set<Vector2d> notPreferredFields = new HashSet<>();

    private final int numberOfElements; // number of plants to plant on a particular day

    public AbstractVegetation(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    /**
     * vegetate creates randomly 80% of plants to create on preferred fields and the other 20% on not preferred ones
     */
    public void vegetate(AbstractWorldMap map) {
        Set<Vector2d> currPreferredFields = getPreferred(map);
        Set<Vector2d> currNotPreferredFields = getNotPreferred(map);

        int preferred = min((int) round(0.8 * numberOfElements), currPreferredFields.size());
        int notPreferred = min(numberOfElements - preferred, currNotPreferredFields.size());

        Map<Vector2d, Grass> plants = new HashMap<>();
        for (Vector2d position : new RandomPositionGenerator(currPreferredFields, preferred))
            plants.put(position, new Grass(position));
        for (Vector2d position : new RandomPositionGenerator(currNotPreferredFields, notPreferred))
            plants.put(position, new Grass(position));
        map.addPlants(plants);
    }

    public abstract Set<Vector2d> getPreferred(AbstractWorldMap map);

    protected abstract Set<Vector2d> getNotPreferred(AbstractWorldMap map);
}
