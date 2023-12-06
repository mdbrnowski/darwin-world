package agh.ics.oop.model;

import java.util.Iterator;
import java.util.List;

public class RandomIterator implements Iterator<Integer> {
    private List<Integer> used;

    public RandomIterator(List<Integer> used){
        this.used =used;

    }
    @Override
    public boolean hasNext() {
        return !used.isEmpty();
    }

    @Override
    public Integer next() {
        return used.remove(0);
    }
}
