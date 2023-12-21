package agh.ics.oop.parameters.enums;

import agh.ics.oop.model.*;

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

    public AbstractVegetation getEquivalentObject(int maxX, int maxY, int numberOfElements) {
        return switch (this) {
            case LIFEGIVINGCORPSES -> new LifeGivingCorpses(maxX, maxY, numberOfElements);
            case FORESTEQUATORS -> new ForestEquators(maxX, maxY, numberOfElements);
        };
    }
}
