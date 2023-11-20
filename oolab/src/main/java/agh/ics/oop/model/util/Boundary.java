package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.Objects;

public record Boundary(Vector2d bottomLeft, Vector2d topRight) {
    public Boundary {
        Objects.requireNonNull(bottomLeft);
        Objects.requireNonNull(topRight);
    }
}
