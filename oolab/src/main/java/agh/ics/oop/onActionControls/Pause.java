package agh.ics.oop.onActionControls;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.AbstractGenome;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.PopularityCounter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Pause {
    public static void pause(Simulation simulation, ImageView pauseButtonImageView, WorldMap map) {
        if (simulation.isStopped()) {
            simulation.resume();
            pauseButtonImageView.setImage(new Image("pause.png"));

        } else {
            simulation.stop();
            pauseButtonImageView.setImage(new Image("resume.png"));
        }
    }

    public static List<List<Vector2d>> highlightGenomes(WorldMap map) {
        var animals = map.getAnimals();
        List<AbstractGenome> mostPopularGenomes = PopularityCounter.getMostPopular(animals.stream()
                        .map(a -> a.getGenome())
                        .collect(Collectors.toList()), 3)
                .stream()
                .map(Pair::getKey)
                .toList();

        List<List<Vector2d>> mostPopularAnimalsPositions = new ArrayList<>();

        int n = 3;
        for (int i = 0; i < n; i++) {
            AbstractGenome genome = mostPopularGenomes.get(i);
            List<Vector2d> currMostPopular = animals.stream()
                    .filter(animal -> animal.getGenome().equals(genome))
                    .map(Animal::getPosition)
                    .toList();
            mostPopularAnimalsPositions.add(currMostPopular);
        }

        return mostPopularAnimalsPositions;

    }
}
