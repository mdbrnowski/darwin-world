package agh.ics.oop.model;

import java.util.List;

public class FullPredestinationGenome extends AbstractGenome {

    public FullPredestinationGenome(List<Integer> genome) {
        super(genome);
    }

    @Override
    public AbstractGenome newInstance(List<Integer> genome) {
        return new FullPredestinationGenome(genome);
    }

    @Override
    public int iterate(int day) {
        return genome.get(day % genome.size());
    }

    @Override
    public int getIterationIndex(int day) {
        return day % genome.size();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof FullPredestinationGenome that)) return false;
        return this.genome.equals(that.getGenome());
    }
}
