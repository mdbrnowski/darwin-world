package agh.ics.oop.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

public class VegetationTest {

    @Test
    public void ForestEquatorsGetPreferredTest() {
        ForestEquators vegetation1 = new ForestEquators(10, 10, 5);
        Set<Vector2d> correct1 = new HashSet<>();

        for (int i = 0; i <= 10; i++)
            correct1.add(new Vector2d(i, 5));

        EarthGlobe map1 = new EarthGlobe(10, 10);

        Assertions.assertEquals(vegetation1.getPreferred(map1), correct1);

        ForestEquators vegetation2 = new ForestEquators(10, 10, 5);
        Set<Vector2d> correct2 = new HashSet<>();

        for (int i = 0; i <= 10; i++)
            if (i != 1 && i != 4) correct2.add(new Vector2d(i, 5));

        EarthGlobe map2 = new EarthGlobe(10, 10);
        Map<Vector2d, Grass> plants = new HashMap<>();
        plants.put(new Vector2d(1, 5), new Grass(new Vector2d(1, 5)));
        plants.put(new Vector2d(4, 5), new Grass(new Vector2d(4, 5)));
        plants.put(new Vector2d(1, 6), new Grass(new Vector2d(1, 6)));

        map2.addPlants(plants);

        Assertions.assertEquals(vegetation2.getPreferred(map2), correct2);
    }

    @Test
    public void ForestEquatorsGetNotPreferredTest() {
        ForestEquators vegetation1 = new ForestEquators(5, 5, 5);
        Set<Vector2d> correct1 = new HashSet<>();
        for (int i = 0; i <= 5; i++)
            for (int j = 0; j <= 5; j++)
                if (j != 2) correct1.add(new Vector2d(i, j));
        EarthGlobe map1 = new EarthGlobe(5, 5);

        Assertions.assertEquals(vegetation1.getNotPreferred(map1), correct1);

        ForestEquators vegetation2 = new ForestEquators(5, 5, 5);

        EarthGlobe map2 = new EarthGlobe(5, 5);

        Map<Vector2d, Grass> plants = new HashMap<>();
        plants.put(new Vector2d(1, 5), new Grass(new Vector2d(1, 5)));
        plants.put(new Vector2d(4, 5), new Grass(new Vector2d(4, 5)));
        plants.put(new Vector2d(2, 2), new Grass(new Vector2d(2, 2)));

        map2.addPlants(plants);

        Set<Vector2d> correct2 = new HashSet<>();
        for (int i = 0; i <= 5; i++)
            for (int j = 0; j <= 5; j++)
                if (!(j == 2 || (i == 1 && j == 5) || (i == 4 && j == 5))) correct2.add(new Vector2d(i, j));

        Assertions.assertEquals(vegetation2.getNotPreferred(map2), correct2);
    }

    @Test
    public void ForestEquatorsVegetateTest() {
        ForestEquators vegetation1 = new ForestEquators(10, 10, 5);
        EarthGlobe map1 = new EarthGlobe(10, 10);
        Set<Vector2d> preferred1 = new HashSet<>();

        for (int i = 0; i <= 10; i++)
            preferred1.add(new Vector2d(i, 5));

        vegetation1.vegetate(map1);
        Assertions.assertEquals(map1.getPlants().size(), 5);

        List<Grass> plants1 = map1.getPlants();
        Set<Vector2d> positions1 = plants1.stream().map(Grass::getPosition).collect(Collectors.toSet());
        preferred1 = preferred1.stream().filter(element -> !positions1.contains(element)).collect(Collectors.toSet());

        Assertions.assertEquals(preferred1.size(), 7);

        ForestEquators vegetation2 = new ForestEquators(10, 10, 5);
        Set<Vector2d> preferred2 = new HashSet<>();

        for (int i = 0; i <= 10; i++)
            if (i != 1 && i != 4) preferred2.add(new Vector2d(i, 5));

        EarthGlobe map2 = new EarthGlobe(10, 10);

        Map<Vector2d, Grass> plants = new HashMap<>();
        plants.put(new Vector2d(1, 5), new Grass(new Vector2d(1, 5)));
        plants.put(new Vector2d(4, 5), new Grass(new Vector2d(4, 5)));
        plants.put(new Vector2d(1, 6), new Grass(new Vector2d(1, 6)));

        map2.addPlants(plants);

        vegetation2.vegetate(map2);
        Assertions.assertEquals(map2.getPlants().size(), 8);

        List<Grass> plants2 = map2.getPlants();
        Set<Vector2d> positions2 = plants2.stream().map(Grass::getPosition).collect(Collectors.toSet());
        preferred2 = preferred2.stream().filter(element -> !positions2.contains(element)).collect(Collectors.toSet());

        Assertions.assertEquals(preferred2.size(), 5);
    }

    @Test
    public void LifeGivingCorpsesGetPreferredTest() {
        LifeGivingCorpses vegetation1 = new LifeGivingCorpses(10, 10, 5);

        EarthGlobe map1 = new EarthGlobe(10, 10);

        Animal animal1 = new Animal(new Vector2d(2, 3), MapDirection.NORTH, 0, new BackAndForthGenome(List.of(1, 2, 3)));
        Animal animal2 = new Animal(new Vector2d(3, 4), MapDirection.SOUTH, 0, new BackAndForthGenome(List.of(1, 7, 3)));
        Animal animal3 = new Animal(new Vector2d(3, 4), MapDirection.EAST, 1, new BackAndForthGenome(List.of(1, 3, 3)));
        Animal animal4 = new Animal(new Vector2d(5, 5), MapDirection.WEST, 2, new BackAndForthGenome(List.of(0, 0, 4)));

        map1.place(animal1);
        map1.place(animal2);
        map1.place(animal3);
        map1.place(animal4);

        map1.removeDead();

        Set<Vector2d> correct1 = Set.of(new Vector2d(1, 4), new Vector2d(2, 4), new Vector2d(3, 4),
                new Vector2d(1, 3), new Vector2d(2, 3), new Vector2d(3, 3), new Vector2d(1, 2),
                new Vector2d(2, 2), new Vector2d(3, 2), new Vector2d(2, 5), new Vector2d(3, 5),
                new Vector2d(4, 5), new Vector2d(4, 4), new Vector2d(4, 3));

        Assertions.assertEquals(vegetation1.getPreferred(map1), correct1);

        LifeGivingCorpses vegetation2 = new LifeGivingCorpses(10, 10, 5);

        EarthGlobe map2 = new EarthGlobe(10, 10);

        animal1 = new Animal(new Vector2d(2, 1), MapDirection.NORTH, 0, new BackAndForthGenome(List.of(1, 2, 3)));
        animal2 = new Animal(new Vector2d(4, 4), MapDirection.SOUTH, 0, new BackAndForthGenome(List.of(1, 7, 3)));
        animal3 = new Animal(new Vector2d(4, 4), MapDirection.EAST, 1, new BackAndForthGenome(List.of(1, 3, 3)));
        animal4 = new Animal(new Vector2d(10, 5), MapDirection.WEST, 2, new BackAndForthGenome(List.of(0, 0, 4)));

        map2.place(animal1);
        map2.place(animal2);
        map2.place(animal3);
        map2.place(animal4);

        Map<Vector2d, Grass> plants = new HashMap<>();
        plants.put(new Vector2d(3, 4), new Grass(new Vector2d(3, 4)));
        plants.put(new Vector2d(3, 1), new Grass(new Vector2d(3, 1)));
        plants.put(new Vector2d(2, 0), new Grass(new Vector2d(2, 0)));
        plants.put(new Vector2d(3, 0), new Grass(new Vector2d(3, 0)));
        plants.put(new Vector2d(10, 5), new Grass(new Vector2d(10, 5)));

        map2.addPlants(plants);
        map2.removeDead();

        Set<Vector2d> correct2 = Set.of(new Vector2d(5, 4), new Vector2d(4, 4),
                new Vector2d(3, 5), new Vector2d(4, 5), new Vector2d(5, 5), new Vector2d(3, 3),
                new Vector2d(4, 3), new Vector2d(5, 3), new Vector2d(1, 2), new Vector2d(2, 2),
                new Vector2d(3, 2), new Vector2d(1, 1), new Vector2d(2, 1), new Vector2d(1, 0));

        Assertions.assertEquals(vegetation2.getPreferred(map2), correct2);
    }

    @Test
    public void LifeGivingCorpsesGetNotPreferredTest() {
        LifeGivingCorpses vegetation1 = new LifeGivingCorpses(10, 10, 5);

        EarthGlobe map1 = new EarthGlobe(10, 10);

        Animal animal1 = new Animal(new Vector2d(2, 3), MapDirection.NORTH, 0, new BackAndForthGenome(List.of(1, 2, 3)));
        Animal animal2 = new Animal(new Vector2d(3, 4), MapDirection.SOUTH, 0, new BackAndForthGenome(List.of(1, 7, 3)));
        Animal animal3 = new Animal(new Vector2d(3, 4), MapDirection.EAST, 1, new BackAndForthGenome(List.of(1, 3, 3)));
        Animal animal4 = new Animal(new Vector2d(5, 5), MapDirection.WEST, 2, new BackAndForthGenome(List.of(0, 0, 4)));

        map1.place(animal1);
        map1.place(animal2);
        map1.place(animal3);
        map1.place(animal4);

        map1.removeDead();

        Set<Vector2d> correct1 = new HashSet<>();

        Set<Vector2d> preferred1 = Set.of(new Vector2d(1, 4), new Vector2d(2, 4), new Vector2d(3, 4),
                new Vector2d(1, 3), new Vector2d(2, 3), new Vector2d(3, 3), new Vector2d(1, 2),
                new Vector2d(2, 2), new Vector2d(3, 2), new Vector2d(2, 5), new Vector2d(3, 5),
                new Vector2d(4, 5), new Vector2d(4, 4), new Vector2d(4, 3));

        for (int i = 0; i <= 10; i++)
            for (int j = 0; j <= 10; j++) {
                Vector2d newVector = new Vector2d(i, j);
                if (!preferred1.contains(newVector)) correct1.add(newVector);
            }

        Assertions.assertEquals(vegetation1.getNotPreferred(map1), correct1);

        LifeGivingCorpses vegetation2 = new LifeGivingCorpses(10, 10, 5);

        EarthGlobe map2 = new EarthGlobe(10, 10);

        animal1 = new Animal(new Vector2d(2, 1), MapDirection.NORTH, 0, new BackAndForthGenome(List.of(1, 2, 3)));
        animal2 = new Animal(new Vector2d(4, 4), MapDirection.SOUTH, 0, new BackAndForthGenome(List.of(1, 7, 3)));
        animal3 = new Animal(new Vector2d(4, 4), MapDirection.EAST, 1, new BackAndForthGenome(List.of(1, 3, 3)));
        animal4 = new Animal(new Vector2d(10, 5), MapDirection.WEST, 2, new BackAndForthGenome(List.of(0, 0, 4)));

        map2.place(animal1);
        map2.place(animal2);
        map2.place(animal3);
        map2.place(animal4);

        Map<Vector2d, Grass> plants = new HashMap<>();
        plants.put(new Vector2d(3, 4), new Grass(new Vector2d(3, 4)));
        plants.put(new Vector2d(3, 1), new Grass(new Vector2d(3, 1)));
        plants.put(new Vector2d(3, 0), new Grass(new Vector2d(3, 0)));
        plants.put(new Vector2d(2, 0), new Grass(new Vector2d(2, 0)));
        plants.put(new Vector2d(10, 5), new Grass(new Vector2d(10, 5)));

        map2.addPlants(plants);

        map2.removeDead();

        Set<Vector2d> correct2 = new HashSet<>();

        Set<Vector2d> preferred2 = Set.of(new Vector2d(5, 4), new Vector2d(4, 4),
                new Vector2d(3, 5), new Vector2d(4, 5), new Vector2d(5, 5), new Vector2d(3, 3),
                new Vector2d(4, 3), new Vector2d(5, 3), new Vector2d(1, 2), new Vector2d(2, 2),
                new Vector2d(3, 2), new Vector2d(1, 1), new Vector2d(2, 1), new Vector2d(1, 0));
        for (int i = 0; i <= 10; i++)
            for (int j = 0; j <= 10; j++) {
                Vector2d newVector = new Vector2d(i, j);
                if (!preferred2.contains(newVector) && !plants.containsKey(newVector)) correct2.add(newVector);
            }

        Assertions.assertEquals(vegetation2.getNotPreferred(map2), correct2);
    }

    @Test
    public void LifeGivingCorpsesVegetationTest() {
        LifeGivingCorpses vegetation1 = new LifeGivingCorpses(10, 10, 7);

        EarthGlobe map1 = new EarthGlobe(10, 10);

        Animal animal1 = new Animal(new Vector2d(2, 3), MapDirection.NORTH, 0, new BackAndForthGenome(List.of(1, 2, 3)));
        Animal animal2 = new Animal(new Vector2d(3, 4), MapDirection.SOUTH, 0, new BackAndForthGenome(List.of(1, 7, 3)));
        Animal animal3 = new Animal(new Vector2d(3, 4), MapDirection.EAST, 1, new BackAndForthGenome(List.of(1, 3, 3)));
        Animal animal4 = new Animal(new Vector2d(5, 5), MapDirection.WEST, 2, new BackAndForthGenome(List.of(0, 0, 4)));

        map1.place(animal1);
        map1.place(animal2);
        map1.place(animal3);
        map1.place(animal4);

        map1.removeDead();
        vegetation1.vegetate(map1);

        Set<Vector2d> preferred1 = Set.of(new Vector2d(1, 4), new Vector2d(2, 4), new Vector2d(3, 4),
                new Vector2d(1, 3), new Vector2d(2, 3), new Vector2d(3, 3), new Vector2d(1, 2),
                new Vector2d(2, 2), new Vector2d(3, 2), new Vector2d(2, 5), new Vector2d(3, 5),
                new Vector2d(4, 5), new Vector2d(4, 4), new Vector2d(4, 3));

        List<Grass> plants1 = map1.getPlants();
        Set<Vector2d> positions1 = plants1.stream().map(Grass::getPosition).collect(Collectors.toSet());
        preferred1 = preferred1.stream().filter(element -> !positions1.contains(element)).collect(Collectors.toSet());

        Assertions.assertEquals(preferred1.size(), 8);
        Assertions.assertEquals(map1.getPlants().size(), 7);

        LifeGivingCorpses vegetation2 = new LifeGivingCorpses(4, 4, 50);

        EarthGlobe map2 = new EarthGlobe(4, 4);

        animal1 = new Animal(new Vector2d(2, 1), MapDirection.NORTH, 0, new BackAndForthGenome(List.of(1, 2, 3)));
        animal2 = new Animal(new Vector2d(4, 4), MapDirection.SOUTH, 0, new BackAndForthGenome(List.of(1, 7, 3)));
        animal3 = new Animal(new Vector2d(4, 4), MapDirection.EAST, 1, new BackAndForthGenome(List.of(1, 3, 3)));
        animal4 = new Animal(new Vector2d(0, 0), MapDirection.WEST, 2, new BackAndForthGenome(List.of(0, 0, 4)));

        map2.place(animal1);
        map2.place(animal2);
        map2.place(animal3);
        map2.place(animal4);

        Map<Vector2d, Grass> plants = new HashMap<>();
        plants.put(new Vector2d(3, 4), new Grass(new Vector2d(3, 4)));
        plants.put(new Vector2d(3, 1), new Grass(new Vector2d(3, 1)));
        plants.put(new Vector2d(3, 0), new Grass(new Vector2d(3, 0)));
        plants.put(new Vector2d(2, 0), new Grass(new Vector2d(2, 0)));
        plants.put(new Vector2d(0, 0), new Grass(new Vector2d(0, 0)));

        map2.addPlants(plants);

        map2.removeDead();
        System.out.println(vegetation2.getPreferred(map2).size() + vegetation2.getNotPreferred(map2).size());

        vegetation2.vegetate(map2);

        Set<Vector2d> preferred2 = Set.of(new Vector2d(4, 4), new Vector2d(3, 3),
                new Vector2d(4, 3), new Vector2d(1, 2), new Vector2d(2, 2),
                new Vector2d(3, 2), new Vector2d(1, 1), new Vector2d(2, 1), new Vector2d(1, 0));

        List<Grass> plants2 = map2.getPlants();
        Set<Vector2d> positions2 = plants2.stream().map(Grass::getPosition).collect(Collectors.toSet());
        preferred2 = preferred2.stream().filter(element -> !positions2.contains(element)).collect(Collectors.toSet());

        System.out.println(positions2.size());

        Assertions.assertEquals(preferred2.size(), 0);
        Assertions.assertEquals(map2.getPlants().size(), 25);
    }

}
