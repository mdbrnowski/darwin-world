package agh.ics.oop.model;

public enum MapDirection {
    NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;

    private static final MapDirection[] directions = values();

    public MapDirection add(int i) {
        if (i < 0 || i > 7)
            throw new IllegalArgumentException("Turn should be an integer in [0,7].");
        return directions[(ordinal() + i) % directions.length];
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "↑";
            case NORTHEAST -> "↗";
            case EAST -> "→";
            case SOUTHEAST -> "↘";
            case SOUTH -> "↓";
            case SOUTHWEST -> "↙";
            case WEST -> "←";
            case NORTHWEST -> "↖";
        };
    }


    public MapDirection reverse() {
        return switch (this) {
            case NORTHEAST -> SOUTHEAST;
            case SOUTHEAST -> NORTHEAST;
            case NORTHWEST -> SOUTHWEST;
            case SOUTHWEST -> NORTHWEST;
            case SOUTH->NORTH;
            case NORTH->SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
        };
    }

}
