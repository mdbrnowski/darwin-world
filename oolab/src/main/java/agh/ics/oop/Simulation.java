package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.PositionAlreadyOccupiedException;
import agh.ics.oop.model.util.RandomPositionGenerator;
import agh.ics.oop.parameters.SimulationParameters;
import agh.ics.oop.parameters.enums.GenomeEnum;
import agh.ics.oop.parameters.enums.VegetationEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static agh.ics.oop.parameters.enums.VegetationEnum.FORESTEQUATORS;

public class Simulation implements Runnable {
    private final AbstractWorldMap map;
    private final List<Animal> animals;
    private AbstractVegetation vegetation;
    private long sleepTime = 0;
    private int minMutations;
    private int maxMutations;

    public Simulation(AbstractWorldMap map, SimulationParameters parameters) {
        this.map = map;
        this.minMutations = parameters.mutationParameters().minMutationNumber();
        this.maxMutations = parameters.mutationParameters().maxMutationNumber();
        this.animals = new ArrayList<>();

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
                animals.add(a);
            } catch (PositionAlreadyOccupiedException ex) {
                System.out.println(ex.getMessage());
            }
        }

        setVegetation(parameters.generalParameters().vegetation(), map,
                parameters.generalParameters().startPlantsCount());
        vegetation.vegatate(map);

        System.out.println(map);
    }

    public Simulation(AbstractWorldMap map, SimulationParameters parameters, long sleepTime) {
        this(map, parameters);
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        // todo: change this
        map.removeDead();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < map.getAnimals().size(); j++) {
                map.move(map.getAnimals().get(j));
                sleep();
            }
            map.nextDay();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Animal makeNewAnimal(Vector2d position, GenomeEnum genomeType, int genomeLength) {
        Random random = new Random();
        MapDirection mapDirection = MapDirection.NORTH;
        mapDirection = mapDirection.add(random.nextInt(8));


        List<Integer> randomList = new ArrayList<>();
        for (int i = 0; i < genomeLength; i++) {
            randomList.add(random.nextInt(8));
        }

        AbstractGenome genome = switch (genomeType) {
            case FULLPREDESTINATIONGENOME -> new FullPredestinationGenome(randomList);
            case BACKANDFORTHGENOME -> new BackAndForthGenome(randomList);
            default -> throw new IllegalArgumentException();
        };
        System.out.println(genome.getGenome());

        Animal animal = new Animal(position, mapDirection, genome);
        return animal;
    }

    private void setVegetation(VegetationEnum vegetationType, AbstractWorldMap map, int plantsCount) {
        AbstractVegetation vegetation = switch (vegetationType) {
            case FORESTEQUATORS -> new ForestEquators(map.getCurrentBounds().topRight().getX(),
                    map.getCurrentBounds().topRight().getY(), plantsCount);
            case LIFEGIVINGCORPSES -> new LifeGivingCorpses(map.getCurrentBounds().topRight().getX(),
                    map.getCurrentBounds().topRight().getY(), plantsCount);
            default -> throw new IllegalArgumentException();
        };

        this.vegetation = vegetation;
    }
}
