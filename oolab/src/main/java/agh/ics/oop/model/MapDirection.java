package agh.ics.oop.model;

public enum MapDirection {
    NORTH, WEST, SOUTH, EAST;

    public String toString() {
        return switch (this) {
            case NORTH -> "Północ";
            case WEST -> "Zachód";
            case SOUTH -> "Południe";
            case EAST -> "Wschód";
        };
    }

    public MapDirection next() {
        return switch (this) {
            case NORTH -> EAST;
            case WEST -> NORTH;
            case SOUTH -> WEST;
            case EAST -> SOUTH;
        };
    }

    public MapDirection previous() {
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
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
