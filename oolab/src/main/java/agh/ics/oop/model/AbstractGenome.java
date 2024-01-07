package agh.ics.oop.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        return genome.stream().map(Objects::toString).collect(Collectors.joining());
    }
}
