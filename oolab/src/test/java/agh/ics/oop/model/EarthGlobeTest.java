package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class EarthGlobeTest {
    @Test
    public void getNextPositionTest() {
        EarthGlobe map = new EarthGlobe(5, 6);
        Vector2d position1 = new Vector2d(5, 6);
        Vector2d move1 = new Vector2d(1, 1);

        Assertions.assertEquals(map.getNextPosition(position1, move1), new Vector2d(0, 6));

        Vector2d position2 = new Vector2d(2, 3);
        Assertions.assertEquals(map.getNextPosition(position2, new Vector2d(1, 0)), new Vector2d(3, 3));
        Assertions.assertEquals(map.getNextPosition(new Vector2d(0, 0), new Vector2d(-1, 2)),
                new Vector2d(5, 2));
        Assertions.assertEquals(map.getNextPosition(new Vector2d(0, 0), new Vector2d(-1, -1)),
                new Vector2d(5, 0));
    }

    @Test
    public void moveTest() {
        EarthGlobe map1 = new EarthGlobe(10, 10);
        Animal animal1 = new Animal(new Vector2d(9, 10), MapDirection.WEST, 1,
                new BackAndForthGenome(List.of(1, 2, 3, 4, 5, 6)));

        map1.place(animal1);
        map1.move(animal1);

        Assertions.assertEquals(animal1.getOrientation(), MapDirection.SOUTHWEST);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(8, 10));

        animal1.incrementAge();
        map1.move(animal1);

        Assertions.assertEquals(animal1.getOrientation(), MapDirection.SOUTHWEST);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(7, 10));

        animal1.incrementAge();
        map1.move(animal1);

        Assertions.assertEquals(animal1.getOrientation(), MapDirection.SOUTH);
        Assertions.assertEquals(animal1.getPosition(), new Vector2d(7, 10));

        Animal animal2 = new Animal(new Vector2d(0, 0), MapDirection.SOUTH, 1,
                new BackAndForthGenome(List.of(2, 7, 5, 2, 0, 0)));

        map1.place(animal2);
        map1.move(animal2);

        Assertions.assertEquals(animal2.getOrientation(), MapDirection.WEST);
        Assertions.assertEquals(animal2.getPosition(), new Vector2d(10, 0));

        animal2.incrementAge();
        map1.move(animal2);

        Assertions.assertEquals(animal2.getOrientation(), MapDirection.NORTHWEST);
        Assertions.assertEquals(animal2.getPosition(), new Vector2d(9, 0));

        animal2.incrementAge();
        map1.move(animal2);

        Assertions.assertEquals(animal2.getOrientation(), MapDirection.NORTH);
        Assertions.assertEquals(animal2.getPosition(), new Vector2d(9, 0));

        animal2.incrementAge();
        map1.move(animal2);

        Assertions.assertEquals(animal2.getOrientation(), MapDirection.EAST);
        Assertions.assertEquals(animal2.getPosition(), new Vector2d(10, 0));

        animal2.incrementAge();
        map1.move(animal2);

        Assertions.assertEquals(animal2.getOrientation(), MapDirection.EAST);
        Assertions.assertEquals(animal2.getPosition(), new Vector2d(0, 0));

        int width = 2;
        int height = 3;
        EarthGlobe globe = new EarthGlobe(width, height);
        globe.addObserver(new ConsoleMapDisplay());

        FullPredestinationGenome genome = new FullPredestinationGenome(List.of(0, 2, 3));
        Animal animal6 = new Animal(new Vector2d(1, height), MapDirection.NORTH, 0, genome);
        Animal animal7 = new Animal(new Vector2d(width, 0), MapDirection.EAST, 0, genome);
        Animal animal3 = new Animal(new Vector2d(0, 2), MapDirection.WEST, 0, genome);
        Animal animal4 = new Animal(new Vector2d(1, 0), MapDirection.SOUTH, 0, genome);
        Animal animal5 = new Animal(new Vector2d(0, height), MapDirection.NORTHWEST, 0, genome);

        globe.place(animal6);
        globe.place(animal7);
        globe.place(animal3);
        globe.place(animal4);
        globe.place(animal5);

        globe.move(animal7);
        globe.move(animal3);
        globe.move(animal6);
        globe.move(animal4);
        globe.move(animal5);

        Assertions.assertEquals(animal6.getPosition(), new Vector2d(1, 3));
        Assertions.assertEquals(animal7.getPosition(), new Vector2d(0, 0));
        Assertions.assertEquals(animal3.getPosition(), new Vector2d(2, 2));
        Assertions.assertEquals(animal4.getPosition(), new Vector2d(1, 0));
        Assertions.assertEquals(animal5.getPosition(), new Vector2d(2, 3));

        Assertions.assertEquals(animal6.getOrientation(), MapDirection.SOUTH);
        Assertions.assertEquals(animal7.getOrientation(), MapDirection.EAST);
        Assertions.assertEquals(animal3.getOrientation(), MapDirection.WEST);
        Assertions.assertEquals(animal4.getOrientation(), MapDirection.NORTH);
        Assertions.assertEquals(animal5.getOrientation(), MapDirection.SOUTHWEST);
    }

    @Test
    public void getNumberOfEmptyFieldsTest() {
        EarthGlobe map = new EarthGlobe(5, 5);

        Map<Vector2d, Grass> plants = new HashMap<>();

        List<Grass> grass = List.of(new Grass(new Vector2d(2, 3)), new Grass(new Vector2d(4, 4)),
                new Grass(new Vector2d(5, 5)), new Grass(new Vector2d(2, 0)),
                new Grass(new Vector2d(2, 5)));
        for (Grass g : grass) {
            plants.put(g.getPosition(), g);
        }

        map.addPlants(plants);

        List<Animal> animals = List.of(
                new Animal(new Vector2d(2, 3), MapDirection.NORTH, 0,
                        new FullPredestinationGenome(List.of(0, 3, 6))),
                new Animal(new Vector2d(1, 2), MapDirection.NORTHWEST, 0,
                        new FullPredestinationGenome(List.of(0, 3, 3))),
                new Animal(new Vector2d(0, 0), MapDirection.SOUTH, 0,
                        new FullPredestinationGenome(List.of(1, 4, 5))),
                new Animal(new Vector2d(5, 4), MapDirection.WEST, 0,
                        new FullPredestinationGenome(List.of(5, 7, 7))),
                new Animal(new Vector2d(3, 3), MapDirection.SOUTHEAST, 0,
                        new FullPredestinationGenome(List.of(2, 3, 2))),
                new Animal(new Vector2d(2, 2), MapDirection.NORTH, 0,
                        new FullPredestinationGenome(List.of(5, 2, 4))),
                new Animal(new Vector2d(2, 1), MapDirection.NORTH, 0,
                        new FullPredestinationGenome(List.of(0, 2, 2))));

        for (Animal animal : animals)
            map.place(animal);

        Assertions.assertEquals(map.getNumberOfEmptyFields(), 29);
    }

    @Test
    public void removeDeadTest() {
        EarthGlobe map = new EarthGlobe(5, 5);
        Map<Vector2d, Grass> plants = new HashMap<>();

        List<Grass> grass = List.of(new Grass(new Vector2d(2, 3)), new Grass(new Vector2d(4, 4)),
                new Grass(new Vector2d(5, 5)), new Grass(new Vector2d(2, 0)),
                new Grass(new Vector2d(2, 5)));
        for (Grass g : grass)
            plants.put(g.getPosition(), g);

        map.addPlants(plants);

        Animal animal1 = new Animal(new Vector2d(2, 3), MapDirection.NORTH, 1,
                new FullPredestinationGenome(List.of(0, 3, 6)));
        Animal animal2 = new Animal(new Vector2d(1, 2), MapDirection.NORTHWEST, 2,
                new FullPredestinationGenome(List.of(0, 3, 3)));
        Animal animal3 = new Animal(new Vector2d(0, 0), MapDirection.SOUTH, 0,
                new FullPredestinationGenome(List.of(1, 4, 5)));
        Animal animal4 = new Animal(new Vector2d(5, 4), MapDirection.WEST, 1,
                new FullPredestinationGenome(List.of(5, 7, 7)));
        Animal animal5 = new Animal(new Vector2d(3, 3), MapDirection.SOUTHEAST, 0,
                new FullPredestinationGenome(List.of(2, 3, 2)));
        Animal animal6 = new Animal(new Vector2d(2, 2), MapDirection.NORTH, 0,
                new FullPredestinationGenome(List.of(5, 2, 4)));
        Animal animal7 = new Animal(new Vector2d(2, 1), MapDirection.NORTH, 4,
                new FullPredestinationGenome(List.of(0, 2, 2)));

        List<Animal> animals = List.of(animal1, animal2, animal3, animal4, animal5, animal6, animal7);

        for (Animal animal : animals)
            map.place(animal);

        Assertions.assertEquals(new HashSet<>(animals), new HashSet<>(map.getAnimals()));
        map.removeDead();

        animals = List.of(animal1, animal2, animal4, animal7);
        Assertions.assertEquals(new HashSet<>(animals), new HashSet<>(map.getAnimals()));

        Set<Animal> deadAnimals = Set.of(animal3, animal5, animal6);
        Set<Vector2d> recentlyDead = Set.of(new Vector2d(0, 0), new Vector2d(3, 3), new Vector2d(2, 2));

        Assertions.assertEquals(recentlyDead, map.getRecentlyDead());
        Assertions.assertEquals(deadAnimals, new HashSet<>(map.getDeadAnimals()));

        animal1.decreaseEnergy(1);
        animal4.decreaseEnergy(1);
        map.removeDead();

        deadAnimals = Set.of(animal3, animal5, animal6, animal1, animal4);
        recentlyDead = Set.of(new Vector2d(2, 3), new Vector2d(5, 4));

        Assertions.assertEquals(recentlyDead, map.getRecentlyDead());
        Assertions.assertEquals(deadAnimals, new HashSet<>(map.getDeadAnimals()));

        animals = List.of(animal2, animal7);
        Assertions.assertEquals(new HashSet<>(animals), new HashSet<>(map.getAnimals()));
    }
}
