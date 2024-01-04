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
    public boolean equals(Object obj) {
        FullPredestinationGenome that = (FullPredestinationGenome) obj;
        return this.genome.equals(that.getGenome());
    }
}
