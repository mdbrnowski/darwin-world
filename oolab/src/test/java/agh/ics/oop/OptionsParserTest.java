package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OptionsParserTest {
    @Test
    public void parseTest() {
        String[] args = {"f", "b", "r", "l"};
        MoveDirection[] result = {MoveDirection.FORWARD, MoveDirection.BACKWARD,
                MoveDirection.RIGHT, MoveDirection.LEFT};
        assertArrayEquals(OptionsParser.parse(args).toArray(), result);
    }

    @Test
    public void parseWithExceptionTest() {
        String[] args = {"f", "b", "s", "r", "l"};
        assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(args).toArray());
    }
}