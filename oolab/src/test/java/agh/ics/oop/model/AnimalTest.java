package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AnimalTest {
    @Test
    public void sortFirstTest() {
        Animal a = new Animal(null, null, 3, null);
        Animal b = new Animal(null, null, 2, null);
        Animal c = new Animal(null, null, 1, null);

        List<Animal> animals = new java.util.ArrayList<>(List.of(a, b, c));
        Collections.sort(animals);
        Assertions.assertEquals(List.of(c, b, a), animals);
    }

    @Test
    public void sortSecondTest() {
        Animal a = new Animal(null, null, 1, null);
        Animal b = new Animal(null, null, 1, null);
        Animal c = new Animal(null, null, 1, null);

        a.incrementAge();
        a.incrementAge();
        b.incrementAge();

        List<Animal> animals = new java.util.ArrayList<>(List.of(a, b, c));
        animals.sort(Collections.reverseOrder());
        Assertions.assertEquals(List.of(a, b, c), animals);
    }

    @Test
    public void sortThirdTest() {
        Animal a = new Animal(null, null, 1, null);
        Animal b = new Animal(null, null, 1, null);
        Animal c = new Animal(null, null, 1, null);

        a.incrementChildrenNum();
        a.incrementChildrenNum();
        b.incrementChildrenNum();

        List<Animal> animals = new java.util.ArrayList<>(List.of(a, b, c));
        animals.sort(Collections.reverseOrder());
        Assertions.assertEquals(List.of(a, b, c), animals);
    }

    @Test
    public void sortAllTest() {
        Animal a = new Animal(null, null, 1, null);
        Animal b = new Animal(null, null, 1, null);
        Animal c = new Animal(null, null, 2, null);
        Animal d = new Animal(null, null, 2, null);

        a.incrementAge();
        d.incrementChildrenNum();

        List<Animal> animals = new java.util.ArrayList<>(List.of(a, b, c, d));
        animals.sort(Collections.reverseOrder());
        Assertions.assertEquals(List.of(d, c, a, b), animals);
    }

    @Test
    public void breedTest() {
        Animal a = new Animal(new Vector2d(1, 2), MapDirection.NORTH, 3,
                new BackAndForthGenome(List.of(0, 1, 2, 3, 4, 5)));
        Animal b = new Animal(new Vector2d(1, 2), MapDirection.SOUTH, 6,
                new BackAndForthGenome(List.of(6, 7, 0, 1, 2, 3)));

        b.incrementChildrenNum();
        b.incrementChildrenNum();

        int energyForChild = 2;
        int minMutations = 0;
        int maxMutations = 0;

        Animal child1 = a.breed(b, minMutations, maxMutations, energyForChild);

        Assertions.assertEquals(child1.getEnergy(), 4);
        Assertions.assertEquals(child1.getPosition(), a.getPosition());
        Assertions.assertEquals(child1.getGenome(), new BackAndForthGenome(List.of(0, 1, 0, 1, 2, 3)));

        Assertions.assertEquals(a.getEnergy(), 1);
        Assertions.assertEquals(b.getEnergy(), 4);

        Assertions.assertEquals(a.getChildrenNum(), 1);
        Assertions.assertEquals(b.getChildrenNum(), 3);

        Animal c = new Animal(new Vector2d(3, 4), MapDirection.EAST, 10,
                new FullPredestinationGenome(List.of(0, 1, 2, 3, 4, 5, 6, 7)));
        Animal d = new Animal(new Vector2d(3, 4), MapDirection.SOUTH, 5,
                new FullPredestinationGenome(List.of(6, 7, 0, 1, 2, 3, 0, 2)));

        d.incrementChildrenNum();

        int energyForChild2 = 5;
        int minMutations2 = 1;
        int maxMutations2 = 2;

        Animal child2 = c.breed(d, minMutations2, maxMutations2, energyForChild2);

        Assertions.assertEquals(child2.getEnergy(), 10);
        Assertions.assertEquals(child2.getPosition(), new Vector2d(3, 4));

        List<Integer> correct = List.of(0, 1, 2, 3, 4, 5, 0, 2);
        int diff = 0;
        List<Integer> genome = child2.getGenome().getGenome();

        for (int i = 0; i < correct.size(); i++) {
            if (!Objects.equals(correct.get(i), genome.get(i))) diff++;
        }
        Assertions.assertTrue(diff >= 1 && diff <= 2);

        Assertions.assertEquals(c.getEnergy(), 5);
        Assertions.assertEquals(d.getEnergy(), 0);

        Assertions.assertEquals(c.getChildrenNum(), 1);
        Assertions.assertEquals(d.getChildrenNum(), 2);

    }
}
