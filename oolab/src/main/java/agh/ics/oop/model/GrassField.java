package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int numberOfElements) {
        int max = (int) sqrt(numberOfElements * 10);
        for (Vector2d position : new RandomPositionGenerator(0, max, numberOfElements)) {
            grasses.put(position, new Grass(position));
        }
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (super.isOccupied(position))
            return animals.get(position);
        return grasses.get(position);
    }

    @Override
    public String toString() {
        Vector2d topRight = new Vector2d(0, 0);
        Vector2d bottomLeft = new Vector2d(0, 0);
        for (Vector2d v : grasses.keySet()) {
            topRight = topRight.upperRight(v);
            bottomLeft = bottomLeft.lowerLeft(v);
        }
        for (Vector2d v : animals.keySet()) {
            topRight = topRight.upperRight(v);
            bottomLeft = bottomLeft.lowerLeft(v);
        }
        return new MapVisualizer<>(this).draw(bottomLeft, topRight);
    }

    @Override
    public Collection<WorldElement> getElements() {
        Collection<WorldElement> result = new ArrayList<>(animals.values());
        result.addAll(grasses.values());
        return result;
    }
}
