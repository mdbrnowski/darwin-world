package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private int n;
    private final List<Vector2d> vectors;

    public RandomPositionGenerator(int min, int max, int n) {
        this.n = n;
        this.vectors = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            for (int j = min; j <= max; j++) {
                vectors.add(new Vector2d(i, j));
            }
        }
        Collections.shuffle(vectors);
    }

    public RandomPositionGenerator(List<Vector2d> choice, int n) {
        this.n = Math.min(n, choice.size());
        this.vectors = choice;
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
