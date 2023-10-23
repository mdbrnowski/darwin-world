package agh.ics.oop;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimulationTest {
    @Test
    public void orientationTest() {
        List<Vector2d> positions = List.of(new Vector2d(0, 0));
        String[] args = {"l", "l", "l", "l", "l"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        Simulation simulation = new Simulation(directions, positions);
        simulation.run();

        var a = simulation.getAnimals().get(0);
        assertSame(a.getOrientation(), MapDirection.WEST);
    }

    @Test
    public void multipleOrientationTest() {
        List<Vector2d> positions = List.of(new Vector2d(0, 0), new Vector2d(4, 4));
        String[] args = {"r", "l", "l", "r", "r", "r", "l", "r"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        Simulation simulation = new Simulation(directions, positions);
        simulation.run();

        var a = simulation.getAnimals().get(0);
        var b = simulation.getAnimals().get(1);
        assertSame(MapDirection.NORTH, a.getOrientation());
        assertSame(MapDirection.SOUTH, b.getOrientation());
    }

    @Test
    public void positionTest() {
        List<Vector2d> positions = List.of(new Vector2d(0, 0), new Vector2d(2, 1));
        String[] args = {"f", "b", "r", "l", "f", "f", "f", "f"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        Simulation simulation = new Simulation(directions, positions);
        simulation.run();

        var a = simulation.getAnimals().get(0);
        var b = simulation.getAnimals().get(1);
        assertTrue(a.isAt(new Vector2d(2, 1)));
        assertTrue(b.isAt(new Vector2d(0, 0)));
    }

    @Test
    public void outOfMapTest() {
        List<Vector2d> positions = List.of(new Vector2d(3, 3));
        String[] args = {"f", "f", "f", "l"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        Simulation simulation = new Simulation(directions, positions);
        simulation.run();

        var a = simulation.getAnimals().get(0);
        assertTrue(a.isAt(new Vector2d(3, 4)));
        assertSame(MapDirection.WEST, a.getOrientation());
    }
}
