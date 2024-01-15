package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final List<Vector2d> vectors;
    private int n;

    public RandomPositionGenerator(Set<Vector2d> choice, int n) {
        this.n = Math.min(n, choice.size());
        this.vectors = new ArrayList<>(choice);
        Collections.shuffle(vectors);
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return n != 0;
            }

            @Override
            public Vector2d next() {
                return vectors.get(--n);
            }
        };
    }
}
