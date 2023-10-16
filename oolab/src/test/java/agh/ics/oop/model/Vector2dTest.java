package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    @Test
    public void equalsTest() {
        var v1 = new Vector2d(1, 2);
        var v2 = new Vector2d(2, 2);
        var v3 = new Vector2d(1, 2);
        assertEquals(v1, v3);
        assertEquals(v3, v1);
        assertNotEquals(v1, v2);
        assertNotEquals(v2, v1);
    }

    @Test
    public void toStringTest() {
        var v = new Vector2d(1, 2);
        assertEquals(v.toString(), "(1,2)");
    }

    @Test
    public void precedesTest() {
        var v1 = new Vector2d(0, 0);
        var v2 = new Vector2d(2, 0);
        var v3 = new Vector2d(2, 2);
        var v4 = new Vector2d(0, 2);
        assertTrue(v1.precedes(v1));
        assertTrue(v1.precedes(v2));
        assertTrue(v1.precedes(v3));
        assertTrue(v1.precedes(v4));
        assertTrue(v2.precedes(v2));
        assertTrue(v2.precedes(v3));
        assertTrue(v4.precedes(v3));
        assertTrue(v4.precedes(v4));
    }

    @Test
    public void followsTest() {
        var v1 = new Vector2d(0, 0);
        var v2 = new Vector2d(2, 0);
        var v3 = new Vector2d(2, 2);
        var v4 = new Vector2d(0, 2);
        assertTrue(v1.follows(v1));
        assertTrue(v2.follows(v1));
        assertTrue(v2.follows(v2));
        assertTrue(v3.follows(v1));
        assertTrue(v3.follows(v2));
        assertTrue(v3.follows(v4));
        assertTrue(v4.follows(v1));
        assertTrue(v4.follows(v4));
    }

    @Test
    public void upperRightTest() {
        var v1 = new Vector2d(0, 0);
        var v2 = new Vector2d(2, 0);
        var v3 = new Vector2d(2, 2);
        var v4 = new Vector2d(0, 2);
        assertEquals(v1.upperRight(v2), v2);
        assertEquals(v1.upperRight(v4), v4);
        assertEquals(v2.upperRight(v4), v3);
    }

    @Test
    public void lowerLeftTest() {
        var v1 = new Vector2d(0, 0);
        var v2 = new Vector2d(2, 0);
        var v3 = new Vector2d(2, 2);
        var v4 = new Vector2d(0, 2);
        assertEquals(v1.lowerLeft(v2), v1);
        assertEquals(v3.lowerLeft(v4), v4);
        assertEquals(v2.lowerLeft(v4), v1);
    }

    @Test
    public void addTest() {
        var v1 = new Vector2d(1, 2);
        var v2 = new Vector2d(-2, -1);
        var v3 = new Vector2d(-1, 1);
        assertEquals(v1.add(v2), v3);
    }

    @Test
    public void subtractTest() {
        var v1 = new Vector2d(1, 2);
        var v2 = new Vector2d(-2, -1);
        var v3 = new Vector2d(3, 3);
        assertEquals(v1.subtract(v2), v3);
    }

    @Test
    public void oppositeTest() {
        var v1 = new Vector2d(1, 2);
        var v2 = new Vector2d(-1, -2);
        assertEquals(v1.opposite(), v2);
        assertEquals(v1.opposite().opposite(), v1);
    }
}
