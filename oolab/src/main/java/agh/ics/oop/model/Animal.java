package agh.ics.oop.model;

public class Animal {
    private MapDirection orientation;
    private Vector2d position;

    public Animal(Vector2d position) {
        this.orientation = MapDirection.NORTH;
        this.position = position;
    }

    public Animal() {
        this(new Vector2d(2, 2));
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }

    public String toString() {
        return switch (orientation) {
            case NORTH -> "↑";
            case WEST -> "←";
            case SOUTH -> "↓";
            case EAST -> "→";
        };
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction, MoveValidator validator) {
        switch (direction) {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> {
                Vector2d new_position = position.add(orientation.toUnitVector());
                if (validator.canMoveTo(new_position))
                    position = new_position;
            }
            case BACKWARD -> {
                Vector2d new_position = position.add(orientation.toUnitVector().opposite());
                if (validator.canMoveTo(new_position))
                    position = new_position;
            }
        }
    }
}
