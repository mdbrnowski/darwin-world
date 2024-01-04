package agh.ics.oop.model;

import java.util.List;

public abstract class AbstractGenome {
    protected List<Integer> genome;

    public AbstractGenome(List<Integer> genome) {
        this.genome = genome;
    }

    public List<Integer> getGenome() {
        return genome;
    }

    abstract AbstractGenome newInstance(List<Integer> genome);

    abstract int iterate(int day);

    @Override
    public abstract boolean equals(Object obj);
}
