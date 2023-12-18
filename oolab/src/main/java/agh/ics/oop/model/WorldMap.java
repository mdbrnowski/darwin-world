package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.PositionAlreadyOccupiedException;

import java.util.List;
import java.util.UUID;

/**
 * The interface responsible for interacting with the map of the world.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends MoveValidator {

    /**
     * Place an animal on the map.
     *
     * @param animal The animal to place on the map.
     */
    void place(Animal animal) throws PositionAlreadyOccupiedException;

    /**
     * Moves an animal forwards.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal animal);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */
    WorldElement objectAt(Vector2d position);

    /**
     * @return list of elements.
     */
    List<WorldElement> getElements();

    /**
     * @return list of animals.
     */
    List<Animal> getAnimals();

    /**
     * @return current bounds (lower left and upper right corners)
     */
    Boundary getCurrentBounds();

    /**
     * @return ID of a map
     */
    UUID getId();

    void mapChanged(String message);

    /**
     * Removes animals that have zero energy
     */
    void removeDead();
}
