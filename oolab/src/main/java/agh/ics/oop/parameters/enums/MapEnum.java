package agh.ics.oop.parameters.enums;

public enum MapEnum {
    EARTHGLOBE("EarthGlobe");
    private final String displayValue;


    MapEnum(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
