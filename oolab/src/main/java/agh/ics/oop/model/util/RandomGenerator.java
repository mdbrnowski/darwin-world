package agh.ics.oop.model.util;

import java.util.*;

/**
 * generates random array of non-repeating numbers
 */
public class RandomGenerator implements Iterable<Integer> {
    private int n;
    private final List<Integer> numbers;

    public RandomGenerator(int max, int n) {
        this.n = n;
        numbers = new ArrayList<>();
        for (int i = 0; i < max; i++)
            numbers.add(i);
        Collections.shuffle(numbers);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return n != 0;
            }

            @Override
            public Integer next() {
                return numbers.get(--n);
            }
        };
    }
}
