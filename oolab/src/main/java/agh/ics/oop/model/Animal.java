package agh.ics.oop.model;

import agh.ics.oop.model.util.DescendantCounter;
import agh.ics.oop.model.util.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Animal implements WorldElement, Comparable<Animal> {
    private MapDirection orientation;
    private Vector2d position;
    private int energy;
    private int childrenNum;
    private int age;
    private int plantsEaten = 0;
    private final AbstractGenome genome;
    private final int id;
    private static int curr_id = 0;
    private Integer diedOn = null;
    public final static String MULTIPLE_ANIMALS_TO_STRING = "⚤";
    private List<Animal> parents = new ArrayList<>();
    private int descendantsNum = 0;


    public Animal(Vector2d position, MapDirection orientation, int energy, AbstractGenome genome) {
        this.position = position;
        this.orientation = orientation;
        this.energy = energy;
        this.genome = genome;
        this.childrenNum = 0;
        this.age = 0;
        id = curr_id;
        curr_id += 1;
    }

    public int getId() {
        return id;
    }

    public void setDiedOn(Integer diedOn) {
        this.diedOn = diedOn;
    }

    public Optional<Integer> getDiedOn() {
        return Optional.ofNullable(diedOn);
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public void setOrientation(MapDirection orientation) {
        this.orientation = orientation;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    @Override
    public String toString() {
        return orientation.toString();
    }

    public void move(Vector2d new_position) {
        position = new_position;
    }

    public int getEnergy() {
        return energy;
    }

    public void eatPlant(int energyFromPlant) {
        energy += energyFromPlant;
        plantsEaten++;
    }

    public void decreaseEnergy(int value) {
        energy -= value;
    }

    public int getPlantsEaten() {
        return plantsEaten;
    }

    public int getChildrenNum() {
        return childrenNum;
    }

    public int getAge() {
        return age;
    }

    public void incrementAge() {
        age += 1;
    }

    public AbstractGenome getGenome() {
        return genome;
    }

    public void incrementChildrenNum() {
        childrenNum += 1;
    }

    public List<Animal> getParents() {
        return parents;
    }

    public void addParent(Animal animal) {
        parents.add(animal);
    }

    public int getDescendantsNum() {
        return descendantsNum;
    }

    public void increaseDescendantsNum(int value) {
        descendantsNum += value;
    }

    public Animal breed(Animal other, int minMutations, int maxMutations, int energyForChild) {

        // create basic genome, without mutations
        double energyPart = (double) this.getEnergy() / (other.getEnergy() + this.getEnergy());
        int genomeSize = genome.genome.size();
        int firstGenome = (int) Math.ceil(energyPart * genomeSize);

        List<Integer> genomeList = new ArrayList<>();
        for (int i = 0; i < firstGenome; i++)
            genomeList.add(this.getGenome().genome.get(i));
        for (int i = firstGenome; i < genomeSize; i++)
            genomeList.add(other.getGenome().genome.get(i));

        // mutate
        Random random = new Random();
        int mutationNumber = (minMutations == maxMutations) ? minMutations :
                random.nextInt((maxMutations - minMutations)) + minMutations;

        RandomGenerator randomGenerator = new RandomGenerator(genomeSize, mutationNumber);
        for (int position : randomGenerator)
            genomeList.set(position, (genomeList.get(position) + 1 + random.nextInt(7)) % 8);
        AbstractGenome newGenome = genome.newInstance(genomeList);

        this.incrementChildrenNum();
        other.incrementChildrenNum();
        this.decreaseEnergy(energyForChild);
        other.decreaseEnergy(energyForChild);

        Animal child = new Animal(position, MapDirection.getRandom(), 2 * energyForChild, newGenome);
        child.addParent(this);
        child.addParent(other);

        DescendantCounter.increaseDescendantsNum(child);
        return child;
    }

    @Override
    public int compareTo(Animal other) {
        if (this.energy != other.energy)
            return this.energy - other.energy;
        if (this.age != other.age)
            return this.age - other.age;
        if (this.childrenNum != other.childrenNum)
            return this.childrenNum - other.childrenNum;
        return 0;
    }
}
