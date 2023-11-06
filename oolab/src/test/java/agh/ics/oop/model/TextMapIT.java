package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextMapIT {
    @Test
    public void sampleTest() {
        TextMap map = new TextMap();
        String a = "Ala";
        String b = "ma";
        String c = "kota";
        map.place(a);
        map.place(b);
        map.place(c);

        assertTrue(map.canMoveTo(0));
        assertFalse(map.canMoveTo(3));
        assertFalse(map.canMoveTo(-1));

        map.move(b, MoveDirection.FORWARD);
        map.move(a, MoveDirection.FORWARD);
        map.move(c, MoveDirection.BACKWARD);

        assertEquals(c, map.objectAt(0));
        assertEquals(a, map.objectAt(1));
        assertEquals(b, map.objectAt(2));
    }

    @Test
    public void outOfMapTest() {
        TextMap map = new TextMap();
        String a = "A";
        String b = "B";
        map.place(a);
        map.place(b);

        map.move(b, MoveDirection.FORWARD);
        map.move(a, MoveDirection.BACKWARD);
        map.move(b, MoveDirection.FORWARD);

        assertEquals(a, map.objectAt(0));
        assertEquals(b, map.objectAt(1));
    }
}
