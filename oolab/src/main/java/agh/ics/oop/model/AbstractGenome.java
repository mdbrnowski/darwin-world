package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGenome {
    protected List<Integer> genome;

    public AbstractGenome(List<Integer> genome){
        this.genome=genome;
    }

    abstract int iterate(int day);

}
