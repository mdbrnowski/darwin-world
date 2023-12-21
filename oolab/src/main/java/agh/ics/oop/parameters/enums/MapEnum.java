package agh.ics.oop.parameters.enums;

import agh.ics.oop.model.*;

public enum MapEnum {
    EARTHGLOBE("EarthGlobe");
    private final String displayValue;


    MapEnum(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public AbstractWorldMap getEquivalentObject(int width, int height) {
        return switch (this) {
            case EARTHGLOBE -> new EarthGlobe(width, height);
        };
    }
}
