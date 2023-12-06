package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class BackAndForthGenome extends AbstractGenome{

    public BackAndForthGenome(List<Integer> genome) {
        super(genome);
    }

    @Override
    public int iterate(int day) {
        int size=genome.size();
        if((day/size)%2==0){
            return genome.get(day%size);
        }
        return genome.get(size-day%size);
    }
}
