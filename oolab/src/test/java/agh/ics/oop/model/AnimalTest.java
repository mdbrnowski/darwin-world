package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class AnimalTest {
    @Test
    public void sortFirstTest() {
        Animal a = new Animal(null, null, null);
        Animal b = new Animal(null, null, null);
        Animal c = new Animal(null, null, null);

        a.setEnergy(3);
        b.setEnergy(2);
        c.setEnergy(1);

        List<Animal> animals = new java.util.ArrayList<>(List.of(a, b, c));
        Collections.sort(animals);
        Assertions.assertEquals(List.of(c, b, a), animals);
    }

    @Test
    public void sortSecondTest() {
        Animal a = new Animal(null, null, null);
        Animal b = new Animal(null, null, null);
        Animal c = new Animal(null, null, null);

        a.incrementAge();
        a.incrementAge();
        b.incrementAge();

        List<Animal> animals = new java.util.ArrayList<>(List.of(a, b, c));
        animals.sort(Collections.reverseOrder());
        Assertions.assertEquals(List.of(a, b, c), animals);
    }

    @Test
    public void sortThirdTest() {
        Animal a = new Animal(null, null, null);
        Animal b = new Animal(null, null, null);
        Animal c = new Animal(null, null, null);

        a.incrementChildrenNum();
        a.incrementChildrenNum();
        b.incrementChildrenNum();

        List<Animal> animals = new java.util.ArrayList<>(List.of(a, b, c));
        animals.sort(Collections.reverseOrder());
        Assertions.assertEquals(List.of(a, b, c), animals);
    }

    @Test
    public void sortAllTest() {
        Animal a = new Animal(null, null, null);
        Animal b = new Animal(null, null, null);
        Animal c = new Animal(null, null, null);
        Animal d = new Animal(null, null, null);

        a.setEnergy(1);
        b.setEnergy(1);
        c.setEnergy(2);
        d.setEnergy(2);

        a.incrementAge();
        d.incrementChildrenNum();

        List<Animal> animals = new java.util.ArrayList<>(List.of(a, b, c, d));
        animals.sort(Collections.reverseOrder());
        Assertions.assertEquals(List.of(d, c, a, b), animals);
    }
}
