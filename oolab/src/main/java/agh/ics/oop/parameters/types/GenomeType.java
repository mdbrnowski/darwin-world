package agh.ics.oop.parameters.types;

import agh.ics.oop.model.AbstractGenome;
import agh.ics.oop.model.BackAndForthGenome;
import agh.ics.oop.model.FullPredestinationGenome;

import java.util.List;

public enum GenomeType {
    FULL_PREDESTINATION_GENOME("FullPredestinationGenome"),
    BACK_AND_FORTH_GENOME("BackAndForthGenome");
    private final String displayValue;

    GenomeType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String toString() {
        return displayValue;
    }

    public AbstractGenome getEquivalentObject(List<Integer> genome) {
        return switch (this) {
            case FULL_PREDESTINATION_GENOME -> new FullPredestinationGenome(genome);
            case BACK_AND_FORTH_GENOME -> new BackAndForthGenome(genome);
        };
    }
}
