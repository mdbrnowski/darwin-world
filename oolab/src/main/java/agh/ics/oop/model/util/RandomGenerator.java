package agh.ics.oop.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * generates random array of non-repeating numbers
 */
public class RandomGenerator implements Iterable<Integer> {
    private final List<Integer> unused = new ArrayList<>();

    private final List<Integer> used = new ArrayList<>();


    public RandomGenerator(int genomeSize, int mutationCount) {
        Random random = new Random();
        for (int i = 0; i < genomeSize; i++) {
            unused.add(i);
        }

        for (int i = 0; i < mutationCount; i++) {
            int size = unused.size();
            int idx = random.nextInt(size);
            int newPosition = unused.remove(idx);
            used.add(newPosition);
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return !used.isEmpty();
            }

            @Override
            public Integer next() {
                return used.remove(0);
            }
        };
    }
}
