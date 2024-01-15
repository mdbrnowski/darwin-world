package agh.ics.oop.model;

import java.util.List;

/**
 * when at the end of the genome, goes back
 */
public class BackAndForthGenome extends AbstractGenome {
    public BackAndForthGenome(List<Integer> genome) {
        super(genome);
    }

    @Override
    public AbstractGenome newInstance(List<Integer> genome) {
        return new BackAndForthGenome(genome);
    }

    @Override
    public int getIterationIndex(int day) {
        int size = genome.size();
        if ((day / size) % 2 == 0) {
            return day % size;
        }
        return size - 1 - day % size;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof BackAndForthGenome that)) return false;
        return this.genome.equals(that.getGenome());
    }
}
