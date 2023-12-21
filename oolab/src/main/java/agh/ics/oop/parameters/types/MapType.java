package agh.ics.oop.parameters.types;

import agh.ics.oop.model.*;

public enum MapType {
    EARTH_GLOBE("EarthGlobe");
    private final String displayValue;

    MapType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String toString() {
        return displayValue;
    }

    public AbstractWorldMap getEquivalentObject(int width, int height) {
        return switch (this) {
            case EARTH_GLOBE -> new EarthGlobe(width, height);
        };
    }
}
