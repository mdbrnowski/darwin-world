package agh.ics.oop.model;

import java.util.List;

public class FullPredestinationGenome extends AbstractGenome {

    public FullPredestinationGenome(List<Integer> genome) {
        super(genome);
    }

    @Override
    public int iterate(int day) {
        return genome.get(day % genome.size());
    }
}
