package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.PositionAlreadyOccupiedException;
import agh.ics.oop.model.util.RandomPositionGenerator;
import agh.ics.oop.parameters.SimulationParameters;
import agh.ics.oop.parameters.types.GenomeType;
import agh.ics.oop.parameters.types.VegetationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation implements Runnable {
    private final AbstractWorldMap map;
    private AbstractVegetation vegetation;
    private long sleepTime = 0;
    private int minMutations;
    private int maxMutations;

    public Simulation(AbstractWorldMap map, SimulationParameters parameters) {
        this.map = map;
        this.minMutations = parameters.mutationParameters().minMutationNumber();
        this.maxMutations = parameters.mutationParameters().maxMutationNumber();

        List<Vector2d> mapFields = new ArrayList<>();
        for (int i = 0; i < map.getCurrentBounds().topRight().getX(); i++)
            for (int j = 0; j < map.getCurrentBounds().topRight().getY(); j++) {
                mapFields.add(new Vector2d(i, j));
            }

        for (Vector2d position : new RandomPositionGenerator(mapFields,
                parameters.generalParameters().startAnimalsCount())) {
            Animal a = makeNewAnimal(position, parameters.generalParameters().genome(),
                    parameters.generalParameters().genomeLength());
            a.setEnergy(parameters.energyParameters().initialAnimalEnergy());
            try {
                map.place(a);
            } catch (PositionAlreadyOccupiedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        map.mapChanged("All animals placed");

        setVegetation(parameters.generalParameters().vegetation(), map,
                parameters.generalParameters().startPlantsCount());
        vegetation.vegetate(map);
    }

    public Simulation(AbstractWorldMap map, SimulationParameters parameters, long sleepTime) {
        this(map, parameters);
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        sleep();
        while (!map.getAnimals().isEmpty()) {
            map.removeDead();
            var animals = map.getAnimals();
            for (Animal animal : animals) {
                map.move(animal);
                animal.decrementEnergy();
                animal.incrementAge();
            }
            map.nextDay();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Animal makeNewAnimal(Vector2d position, GenomeType genomeType, int genomeLength) {
        Random random = new Random();
        MapDirection mapDirection = MapDirection.NORTH;
        mapDirection = mapDirection.add(random.nextInt(8));


        List<Integer> randomList = new ArrayList<>();
        for (int i = 0; i < genomeLength; i++) {
            randomList.add(random.nextInt(8));
        }

        AbstractGenome genome = genomeType.getEquivalentObject(randomList);
        System.out.println(genome.getGenome());

        return new Animal(position, mapDirection, genome);
    }

    private void setVegetation(VegetationType vegetationType, AbstractWorldMap map, int plantsCount) {
        this.vegetation = vegetationType.getEquivalentObject(map.getCurrentBounds().topRight().getX(),
                map.getCurrentBounds().topRight().getY(), plantsCount);;
    }
}
