package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class MapDirectionTest {
    @Test
    public void nextTest() {
        assertSame(MapDirection.NORTH.next(), MapDirection.EAST);
        assertSame(MapDirection.WEST.next(), MapDirection.NORTH);
        assertSame(MapDirection.SOUTH.next(), MapDirection.WEST);
        assertSame(MapDirection.EAST.next(), MapDirection.SOUTH);
    }

    @Test
    public void previousTest() {
        assertSame(MapDirection.NORTH.previous(), MapDirection.WEST);
        assertSame(MapDirection.WEST.previous(), MapDirection.SOUTH);
        assertSame(MapDirection.SOUTH.previous(), MapDirection.EAST);
        assertSame(MapDirection.EAST.previous(), MapDirection.NORTH);
    }
}

