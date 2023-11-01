package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationIT {
    @Test
    public void orientationTest() {
        WorldMap<Animal, Vector2d> map = new RectangularMap(4, 4);
        List<Vector2d> positions = List.of(new Vector2d(0, 0));
        String[] args = {"l", "l", "l", "l", "l"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        Simulation simulation = new Simulation(map, positions, directions);
        simulation.run();

        var a = simulation.getAnimals().get(0);
        assertSame(a.getOrientation(), MapDirection.WEST);
    }

    @Test
    public void multipleOrientationTest() {
        WorldMap<Animal, Vector2d> map = new RectangularMap(4, 4);
        List<Vector2d> positions = List.of(new Vector2d(0, 0), new Vector2d(4, 4));
        String[] args = {"r", "l", "l", "r", "r", "r", "l", "r"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        Simulation simulation = new Simulation(map, positions, directions);
        simulation.run();

        var a = simulation.getAnimals().get(0);
        var b = simulation.getAnimals().get(1);
        assertSame(MapDirection.NORTH, a.getOrientation());
        assertSame(MapDirection.SOUTH, b.getOrientation());
    }

    @Test
    public void positionTest() {
        WorldMap<Animal, Vector2d> map = new RectangularMap(4, 4);
        List<Vector2d> positions = List.of(new Vector2d(0, 0), new Vector2d(2, 1));
        String[] args = {"f", "b", "r", "l", "f", "f", "f", "f"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        Simulation simulation = new Simulation(map, positions, directions);
        simulation.run();

        var a = simulation.getAnimals().get(0);
        var b = simulation.getAnimals().get(1);
        assertTrue(a.isAt(new Vector2d(2, 1)));
        assertTrue(b.isAt(new Vector2d(0, 0)));
    }

    @Test
    public void outOfMapTest() {
        WorldMap<Animal, Vector2d> map = new RectangularMap(4, 4);
        List<Vector2d> positions = List.of(new Vector2d(1, 1), new Vector2d(3, 3));
        String[] args = {"l", "f", "f", "f", "f", "f", "l", "l"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        Simulation simulation = new Simulation(map, positions, directions);
        simulation.run();

        var a = simulation.getAnimals().get(0);
        var b = simulation.getAnimals().get(1);
        assertTrue(a.isAt(new Vector2d(0, 1)));
        assertSame(MapDirection.SOUTH, a.getOrientation());
        assertTrue(b.isAt(new Vector2d(3, 4)));
        assertSame(MapDirection.WEST, b.getOrientation());
    }

    @Test
    public void sampleTest() {
        WorldMap<Animal, Vector2d> map = new RectangularMap(4, 4);
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
        String[] args = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        Simulation simulation = new Simulation(map, positions, directions);
        simulation.run();

        var a = simulation.getAnimals().get(0);
        var b = simulation.getAnimals().get(1);
        assertTrue(a.isAt(new Vector2d(2, 0)));
        assertSame(MapDirection.SOUTH, a.getOrientation());
        assertTrue(b.isAt(new Vector2d(3, 4)));
        assertSame(MapDirection.NORTH, b.getOrientation());
    }

    @Test
    public void placeAnimalTest() {
        WorldMap<Animal, Vector2d> map = new RectangularMap(4, 4);
        List<Vector2d> positions = List.of(new Vector2d(1, 1), new Vector2d(2, 2), new Vector2d(1, 1));
        String[] args = {"f", "l", "r", "f", "f", "f"};
        List<MoveDirection> directions = OptionsParser.parse(args);
        Simulation simulation = new Simulation(map, positions, directions);
        simulation.run();

        assertEquals(2, simulation.getAnimals().size());
        var a = simulation.getAnimals().get(0);
        var b = simulation.getAnimals().get(1);
        assertTrue(a.isAt(new Vector2d(1, 2)));
        assertSame(MapDirection.EAST, a.getOrientation());
        assertTrue(b.isAt(new Vector2d(2, 2)));
        assertSame(MapDirection.WEST, b.getOrientation());
    }
}
