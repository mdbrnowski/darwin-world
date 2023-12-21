package agh.ics.oop.parameters.enums;

import agh.ics.oop.model.AbstractGenome;
import agh.ics.oop.model.BackAndForthGenome;
import agh.ics.oop.model.FullPredestinationGenome;

import java.util.List;

public enum GenomeEnum {
    FULLPREDESTINATIONGENOME("FullPredestinationGenome"),
    BACKANDFORTHGENOME("BackAndForthGenome");
    private final String displayValue;

    GenomeEnum(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public AbstractGenome getEquivalentObject(List<Integer> genome) {
        return switch (this) {
            case FULLPREDESTINATIONGENOME -> new FullPredestinationGenome(genome);
            case BACKANDFORTHGENOME -> new BackAndForthGenome(genome);
        };
    }
}
