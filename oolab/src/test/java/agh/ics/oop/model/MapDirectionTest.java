package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class MapDirectionTest {
    @Test
    public void addTwoTest() {
        assertSame(MapDirection.NORTH.add(2), MapDirection.EAST);
        assertSame(MapDirection.EAST.add(2), MapDirection.SOUTH);
        assertSame(MapDirection.SOUTH.add(2), MapDirection.WEST);
        assertSame(MapDirection.WEST.add(2), MapDirection.NORTH);
    }

    @Test
    public void addTest() {
        assertSame(MapDirection.NORTH.add(6), MapDirection.WEST);
        assertSame(MapDirection.WEST.add(3), MapDirection.NORTHEAST);
        assertSame(MapDirection.SOUTHEAST.add(7), MapDirection.EAST);
        assertSame(MapDirection.SOUTHWEST.add(5), MapDirection.EAST);
        assertSame(MapDirection.NORTHWEST.add(2), MapDirection.NORTHEAST);
    }
}

