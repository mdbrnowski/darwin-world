package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

import java.util.List;
import java.util.UUID;

public interface WorldMap {

    /**
     * Place an animal on the map.
     *
     * @param animal The animal to place on the map.
     */
    void place(Animal animal);

    /**
     * Moves an animal forwards.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal animal);

    /**
     * Return animals at a given position.
     *
     * @param position The position of the animals.
     * @return list of animals (possibly empty).
     */
    List<Animal> getAnimalsAt(Vector2d position);

    /**
     * @return list of animals.
     */
    List<Animal> getAnimals();

    /**
     * @return list of animals that used to be on the map.
     */
    List<Animal> getDeadAnimals();

    /**
     * Return grass at a given position.
     *
     * @param position The position of the grass.
     * @return grass or null.
     */
    Grass getPlantAt(Vector2d position);

    /**
     * @return list of plants.
     */
    List<Grass> getPlants();

    /**
     * @return current bounds (lower left and upper right corners).
     */
    Boundary getCurrentBounds();

    /**
     * @return ID of a map.
     */
    UUID getId();

    /**
     * Mark that something has been changed in the map.
     *
     * @param message Description what has been changed.
     */
    void mapChanged(String message);

    /**
     * Remove animals that have zero energy.
     */
    void removeDead();

    /**
     * @return number of empty fields (without any animals).
     */
    int getNumberOfEmptyFields();
}
