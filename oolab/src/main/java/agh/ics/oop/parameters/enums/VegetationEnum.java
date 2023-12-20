package agh.ics.oop.parameters.enums;

public enum VegetationEnum {
    LIFEGIVINGCORPSES("LifeGivingCorpses"),
    FORESTEQUATORS("ForestEquators");
    private final String displayValue;


    VegetationEnum(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
