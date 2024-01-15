package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.RandomPositionGenerator;
import agh.ics.oop.parameters.SimulationParameters;
import agh.ics.oop.parameters.types.GenomeType;
import agh.ics.oop.parameters.types.VegetationType;

import java.util.*;
import java.util.stream.Collectors;

public class Simulation implements Runnable {
    private final AbstractWorldMap map;
    private AbstractVegetation vegetation;
    private long sleepTime = 0;
    private final SimulationParameters parameters;
    private boolean stopped = false;
    private boolean ended = false;

    public Simulation(AbstractWorldMap map, SimulationParameters parameters) {
        this.map = map;
        this.parameters = parameters;

        List<Vector2d> mapFields = new ArrayList<>();
        for (int i = 0; i < map.getCurrentBounds().topRight().getX(); i++)
            for (int j = 0; j < map.getCurrentBounds().topRight().getY(); j++) {
                mapFields.add(new Vector2d(i, j));
            }

        for (Vector2d position : new RandomPositionGenerator(mapFields,
                parameters.generalParameters().startAnimalsCount())) {
            Animal a = makeNewAnimal(position, parameters.energyParameters().initialAnimalEnergy(),
                    parameters.generalParameters().genome(), parameters.generalParameters().genomeLength());
            map.place(a);
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
            if (ended) return;
            if (stopped) continue;
            map.nextDay();
            map.removeDead();

            // move and turn
            var animals = map.getAnimals();
            for (Animal animal : animals) {
                map.move(animal);
                animal.decreaseEnergy(1);
                animal.incrementAge();
            }

            // eat plants
            var plants = map.getPlants();
            for (Grass plant : plants) {
                var animalsAt = map.getAnimalsAt(plant.getPosition());
                if (!animalsAt.isEmpty()) {
                    animalsAt.sort(Collections.reverseOrder());
                    Animal best = animalsAt.get(0);
                    best.eatPlant(parameters.energyParameters().energyFromOnePlant());
                    map.removePlant(plant);
                }
            }

            // breed
            var positions = map.getAnimals().stream().map(Animal::getPosition).collect(Collectors.toSet());
            for (Vector2d position : positions) {
                var animalsAt = map.getAnimalsAt(position);
                if (animalsAt.size() >= 2) {
                    animalsAt.sort(Collections.reverseOrder());
                    Animal a = animalsAt.get(0), b = animalsAt.get(1);
                    if (a.getEnergy() >= parameters.energyParameters().minBreedEnergy() &&
                            b.getEnergy() >= parameters.energyParameters().minBreedEnergy()) {
                        Animal c = a.breed(b, parameters.mutationParameters().minMutationNumber(),
                                parameters.mutationParameters().maxMutationNumber(),
                                parameters.energyParameters().energyForChild());
                        map.place(c);
                    }
                }
            }

            vegetation.vegetate(map);
            map.mapChanged("Day %d".formatted(map.getDay()));
            sleep();
        }
    }

    public boolean isStopped() {
        return stopped;
    }

    public void resume() {
        stopped = false;
    }

    public void stop() {
        stopped = true;
    }

    public void end() {
        ended = true;
    }

    private void sleep() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Animal makeNewAnimal(Vector2d position, int energy, GenomeType genomeType, int genomeLength) {
        Random random = new Random();
        MapDirection mapDirection = MapDirection.getRandom();

        List<Integer> randomList = new ArrayList<>();
        for (int i = 0; i < genomeLength; i++)
            randomList.add(random.nextInt(8));

        AbstractGenome genome = genomeType.getEquivalentObject(randomList);

        return new Animal(position, mapDirection, energy, genome);
    }

    public AbstractVegetation getVegetation() {
        return vegetation;
    }

    private void setVegetation(VegetationType vegetationType, AbstractWorldMap map, int plantsCount) {
        this.vegetation = vegetationType.getEquivalentObject(map.getCurrentBounds().topRight().getX(),
                map.getCurrentBounds().topRight().getY(), plantsCount);
    }
}
