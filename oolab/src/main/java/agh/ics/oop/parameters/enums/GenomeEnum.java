package agh.ics.oop.parameters.enums;

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
}
