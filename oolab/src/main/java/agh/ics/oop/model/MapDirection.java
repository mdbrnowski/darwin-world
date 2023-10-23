package agh.ics.oop.model;

public enum MapDirection {
    NORTH, WEST, SOUTH, EAST;

    private static final MapDirection[] directions = values();

    public String toString() {
        return switch (this) {
            case NORTH -> "Północ";
            case WEST -> "Zachód";
            case SOUTH -> "Południe";
            case EAST -> "Wschód";
        };
    }

    public MapDirection next() {
        return directions[(ordinal() - 1 + directions.length) % directions.length];
    }

    public MapDirection previous() {
        return directions[(ordinal() + 1) % directions.length];
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case WEST -> new Vector2d(-1, 0);
            case SOUTH -> new Vector2d(0, -1);
            case EAST -> new Vector2d(1, 0);
        };
    }
}
