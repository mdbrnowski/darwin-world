package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EarthGlobeTest {
    @Test
    public void edgesTest() {
        int width = 2;
        int height = 3;
        int n = 3;
        AbstractVegetation vegetation = new ForestEquators(0, width, 0, height, n);
        EarthGlobe globe = new EarthGlobe(width, height, vegetation);
        globe.addObserver(new ConsoleMapDisplay());

        FullPredestinationGenome genome = new FullPredestinationGenome(List.of(1, 2, 3));
        Animal animal1 = new Animal(new Vector2d(1, height), MapDirection.NORTH, genome);
        Animal animal2 = new Animal(new Vector2d(width, 0), MapDirection.EAST, genome);
        Animal animal3 = new Animal(new Vector2d(0, 2), MapDirection.WEST, genome);
        Animal animal4 = new Animal(new Vector2d(1, 0), MapDirection.SOUTH, genome);
        Animal animal5 = new Animal(new Vector2d(0, height), MapDirection.NORTHWEST, genome);

        Assertions.assertDoesNotThrow(() -> {
                    globe.place(animal1);
                    globe.place(animal2);
                    globe.place(animal3);
                    globe.place(animal4);
                    globe.place(animal5);
                }
        );

        globe.move(animal2);
        globe.move(animal3);
        globe.move(animal1);
        globe.move(animal4);
        globe.move(animal5);

        Assertions.assertEquals(animal1.getPosition(), new Vector2d(1, 3));
        Assertions.assertEquals(animal2.getPosition(), new Vector2d(0, 0));
        Assertions.assertEquals(animal3.getPosition(), new Vector2d(2, 2));
        Assertions.assertEquals(animal4.getPosition(), new Vector2d(1, 0));
        Assertions.assertEquals(animal5.getPosition(), new Vector2d(2, 3));

        Assertions.assertEquals(animal1.getOrientation(), MapDirection.SOUTH);
        Assertions.assertEquals(animal2.getOrientation(), MapDirection.EAST);
        Assertions.assertEquals(animal3.getOrientation(), MapDirection.WEST);
        Assertions.assertEquals(animal4.getOrientation(), MapDirection.NORTH);
        Assertions.assertEquals(animal5.getOrientation(), MapDirection.SOUTHWEST);
    }
}
