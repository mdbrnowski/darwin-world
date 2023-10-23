package agh.ics.oop.model;

public class Animal {
    private MapDirection orientation;
    private Vector2d position;
    private static final Vector2d LOWER_LEFT_CORNER = new Vector2d(0, 0);
    private static final Vector2d UPPER_RIGHT_CORNER = new Vector2d(4, 4);

    public Animal(Vector2d position) {
        this.orientation = MapDirection.NORTH;
        this.position = position;
    }

    public Animal() {
        this(new Vector2d(2, 2));
    }

    public String toString() {
        return "%s -> %s".formatted(position.toString(), orientation.toString());
    }

    public boolean isAt(Vector2d position) {
        return this.position == position;
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> position = position.add(orientation.toUnitVector());
            case BACKWARD -> position = position.add(orientation.toUnitVector().opposite());
        }
        position = position.lowerLeft(UPPER_RIGHT_CORNER);
        position = position.upperRight(LOWER_LEFT_CORNER);
    }
}
