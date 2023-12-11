package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.*;

public abstract class AbstractVegetation {
    protected List<Vector2d> preferredFields = new ArrayList<>();
    protected List<Vector2d> notPreferredFields = new ArrayList<>();

    private final int numberOfElements; //number of plants to plant on a particular day

    public AbstractVegetation(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
  
    /*
     * vegetate creates randomly 80% of plants to create on preferred fields and the other 20% on not preferred ones
     */
    public Map<Vector2d, Grass> vegatate(AbstractWorldMap map) {

        List<Vector2d> currPreferredFields = getPreferred(map);
        List<Vector2d> currNotPreferredFields = getNotPreferred(map);

        int preferred = min((int) round(0.8 * numberOfElements), currPreferredFields.size());

        Map<Vector2d, Grass> plants = new HashMap<>();

        for (Vector2d position : new RandomPositionGenerator(currPreferredFields, preferred)) {
            plants.put(position, new Grass(position));
        }

        int notPreferred = min(numberOfElements - preferred, currNotPreferredFields.size());

        for (Vector2d position : new RandomPositionGenerator(currNotPreferredFields, notPreferred)) {

            plants.put(position, new Grass(position));
        }
        return plants;

    }

    public abstract List<Vector2d> getPreferred(AbstractWorldMap map);

    public abstract List<Vector2d> getNotPreferred(AbstractWorldMap map);
}
