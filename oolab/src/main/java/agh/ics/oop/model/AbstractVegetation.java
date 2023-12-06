package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.*;

public abstract class AbstractVegetation {
    protected List<Vector2d> preferredFields;
    protected List<Vector2d> notPreferredFields;

    private int numberOfElements; //number of plants to plant on a particular day

    public AbstractVegetation(int numberOfElements){
        this.numberOfElements=numberOfElements;
    }

    //vegetate creates randomly 80% of plants to create on preferred fields and the other 20% on not preferred ones
    public Map<Vector2d, Grass> vegatate(int minY,int maxY){
        int preferred=min((int) round(0.8*numberOfElements), maxY-minY);
        Map<Vector2d, Grass> plants = new HashMap<>();

        for (Vector2d position : new RandomPositionGenerator(preferredFields, preferred)) {
            plants.put(position, new Grass(position));
        }

        for (Vector2d position : new RandomPositionGenerator( notPreferredFields,numberOfElements-preferred)) {
            plants.put(position, new Grass(position));
        }
        return plants;

    }
}
