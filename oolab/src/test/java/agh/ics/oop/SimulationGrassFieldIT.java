package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimulationGrassFieldIT {
    @Test
    public void infiniteMapTest() {
        WorldMap<Animal, Vector2d> map = new GrassField(10);
        List<Vector2d> positions = List.of(new Vector2d(1, 1), new Vector2d(3, 3));
        String[] args = {"l", "f", "f", "f", "f", "f", "l", "l"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        Simulation simulation = new Simulation(map, positions, directions);
        simulation.run();

        var a = simulation.getAnimals().get(0);
        var b = simulation.getAnimals().get(1);
        assertTrue(a.isAt(new Vector2d(-1, 1)));
        assertSame(MapDirection.SOUTH, a.getOrientation());
        assertTrue(b.isAt(new Vector2d(3, 6)));
        assertSame(MapDirection.WEST, b.getOrientation());
    }

    @Test
    public void sampleTest() {
        WorldMap<Animal, Vector2d> map = new GrassField(10);
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
        String[] args = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        Simulation simulation = new Simulation(map, positions, directions);
        simulation.run();

        var a = simulation.getAnimals().get(0);
        var b = simulation.getAnimals().get(1);
        assertTrue(a.isAt(new Vector2d(2, -1)));
        assertSame(MapDirection.SOUTH, a.getOrientation());
        assertTrue(b.isAt(new Vector2d(3, 7)));
        assertSame(MapDirection.NORTH, b.getOrientation());
    }
}
