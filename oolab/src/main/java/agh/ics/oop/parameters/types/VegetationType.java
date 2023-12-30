package agh.ics.oop.parameters.types;

import agh.ics.oop.model.*;

public enum VegetationType {
    LIFE_GIVING_CORPSES("LifeGivingCorpses"),
    FOREST_EQUATORS("ForestEquators");
    private final String displayValue;


    VegetationType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String toString() {
        return displayValue;
    }

    public AbstractVegetation getEquivalentObject(int maxX, int maxY, int numberOfElements) {
        return switch (this) {
            case LIFE_GIVING_CORPSES -> new LifeGivingCorpses(maxX, maxY, numberOfElements);
            case FOREST_EQUATORS -> new ForestEquators(maxX, maxY, numberOfElements);
        };
    }
}
