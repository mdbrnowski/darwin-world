package agh.ics.oop.onActionControls;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.PopularityCounter;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Pause {
    private static final Image pauseImage = new Image("pause.png");
    private static final Image resumeImage = new Image("resume.png");

    private boolean isTracked = false;
    private Animal trackedAnimal;

    public static void pause(Simulation simulation, ImageView pauseButtonImageView, Button highlightGenomeButton,
                             Button highlightPreferredButton) {
        if (simulation.isStopped()) {
            simulation.resume();
            pauseButtonImageView.setImage(pauseImage);
            highlightGenomeButton.setVisible(false);
            highlightGenomeButton.setManaged(false);
            highlightPreferredButton.setVisible(false);
            highlightPreferredButton.setManaged(false);
        } else {
            simulation.stop();
            pauseButtonImageView.setImage(resumeImage);
            highlightGenomeButton.setVisible(true);
            highlightGenomeButton.setManaged(true);
            highlightPreferredButton.setVisible(true);
            highlightPreferredButton.setManaged(true);
        }
    }

    public HashMap<Vector2d, Double> highlightGenomes(WorldMap map) {
        var animals = map.getAnimals();
        List<Pair<String, Integer>> mostPopular = PopularityCounter.getMostPopular(animals.stream()
                .map(animal -> animal.getGenome().toString())
                .collect(Collectors.toList()), 3);

        List<Integer> weights = mostPopular
                .stream()
                .map(Pair::getValue)
                .toList();
        int weightSum = weights.stream()
                .reduce(0, Integer::sum);

        List<List<Vector2d>> mostPopularAnimalsPositions = new ArrayList<>();

        for (Pair<String, Integer> stringIntegerPair : mostPopular) {
            String genome = stringIntegerPair.getKey();
            List<Vector2d> currMostPopular = animals.stream()
                    .filter(animal -> genome.equals(animal.getGenome().toString()))
                    .map(Animal::getPosition)
                    .toList();
            mostPopularAnimalsPositions.add(currMostPopular);
        }
        HashMap<Vector2d, Double> allPositions = new HashMap<>();

        for (int i = mostPopular.size() - 1; i >= 0; i--) {
            int m = mostPopularAnimalsPositions.get(i).size();
            for (int j = 0; j < m; j++) {
                Vector2d vector = mostPopularAnimalsPositions.get(i).get(j);
                allPositions.remove(vector);
                allPositions.put(vector, (double) weights.get(i) / weightSum);
            }
        }

        return allPositions;
    }

    public List<Vector2d> highlightPreferred(AbstractVegetation vegetation, WorldMap map) {
        return vegetation.getPreferred((AbstractWorldMap) map);
    }

    public void showAnimalStats(Label label, List<Animal> animals, Stage stage, int day) {
        String style = label.getStyle();
        BorderStroke borderStroke = new BorderStroke(
                Color.BLUE,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(2)
        );

        Border border = new Border(borderStroke);
        label.setBorder(border);
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        dialog.setTitle("Animal Info");

        VBox dialogVbox = new VBox(10);
        Label titleLabel = new Label(String.format("Animals on field (%d,%d):",
                animals.get(0).getPosition().x(), animals.get(0).getPosition().y()));
        titleLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
        Label promptLabel = new Label("You can choose an animal to track");
        promptLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 13));
        dialogVbox.getChildren().add(titleLabel);

        GridPane animalInfoGridPane = new GridPane();

        int label_num = 8;

        for (int i = 0; i < animals.size(); i++) {
            Label animalIdLabel = new Label("Animal id:");
            Label animalIdValueLabel = new Label(String.format("%s", animals.get(i).getId()));
            Label genomeLabel = new Label("Genome:");
            Label genomeValueLabel = new Label();
            genomeValueLabel.setGraphic(getGenomeLabelContent(animals.get(i), day));
            Label energyLabel = new Label("Energy:");
            Label energyValueLabel = new Label(String.format("%d", animals.get(i).getEnergy()));
            Label childrenLabel = new Label(("Children number:"));
            Label childrenValueLabel = new Label(String.format("%d", animals.get(i).getChildrenNum()));
            Label ageLabel = new Label("Age:");
            Label ageValueLabel = new Label(String.format(" %s", animals.get(i).getAge()));
            Label descendantsNumLabel = new Label("Descendants number:");
            Label descendantsNumValueLabel = new Label(String.format(" %s", animals.get(i).getDescendantsNum()));
            Label plantsEatenLabel = new Label("Plants eaten: ");
            Label plantsEatenValueLabel = new Label(String.format(" %s", animals.get(i).getPlantsEaten()));
            Button trackButton = new Button("Track");
            Animal animal = animals.get(i);
            trackButton.setOnAction(e -> startTracking(animal, dialog));

            animalIdLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 15));

            animalInfoGridPane.add(animalIdLabel, 0, i * label_num);
            animalInfoGridPane.add(animalIdValueLabel, 1, i * label_num);
            animalInfoGridPane.add(genomeLabel, 0, i * label_num + 1);
            animalInfoGridPane.add(genomeValueLabel, 1, i * label_num + 1);
            animalInfoGridPane.add(energyLabel, 0, i * label_num + 2);
            animalInfoGridPane.add(energyValueLabel, 1, i * label_num + 2);
            animalInfoGridPane.add(childrenLabel, 0, i * label_num + 3);
            animalInfoGridPane.add(childrenValueLabel, 1, i * label_num + 3);
            animalInfoGridPane.add(descendantsNumLabel, 0, i * label_num + 4);
            animalInfoGridPane.add(descendantsNumValueLabel, 1, i * label_num + 4);
            animalInfoGridPane.add(plantsEatenLabel, 0, i * label_num + 5);
            animalInfoGridPane.add(plantsEatenValueLabel, 1, i * label_num + 5);
            animalInfoGridPane.add(ageLabel, 0, i * label_num + 6);
            animalInfoGridPane.add(ageValueLabel, 1, i * label_num + 6);
            animalInfoGridPane.add(trackButton, 0, i * label_num + 7);
        }
        ScrollPane scrollAnimalInfo = new ScrollPane(animalInfoGridPane);
        scrollAnimalInfo.setMinWidth(animalInfoGridPane.getPrefWidth());
        scrollAnimalInfo.setPrefWidth(220);
        scrollAnimalInfo.setPrefHeight(200);
        HBox scrollHBox = new HBox(scrollAnimalInfo);
        animalInfoGridPane.setAlignment(Pos.CENTER);
        scrollAnimalInfo.setStyle("-fx-padding: 20px, 20px, 20px,20px");
        scrollHBox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(scrollHBox);

        dialogVbox.setAlignment(Pos.CENTER);

        Scene dialogScene = new Scene(dialogVbox, 350, 270);
        dialog.setScene(dialogScene);
        dialog.setOnCloseRequest(a -> {
            label.setStyle(style);
            label.setBorder(null);
        });
        dialog.show();
    }

    public Animal getTrackedAnimal() {
        return trackedAnimal;
    }

    public void stopTracking() {
        isTracked = false;
    }

    public boolean isTracked() {
        return isTracked;
    }

    private void startTracking(Animal animal, Stage dialog) {
        isTracked = true;
        trackedAnimal = animal;
        dialog.close();
    }

    public HBox getGenomeLabelContent(Animal animal, int day) {
        String genome = animal.getGenome().toString();
        int activeGene = animal.getGenome().getIterationIndex(day);
        Text beforeActive = new Text(genome.substring(0, activeGene));
        Text active = new Text(genome.substring(activeGene, Math.min(activeGene + 1, genome.length())));
        active.setFill(Color.RED);
        active.setStyle("-fx-font-weight: bold;");
        Text afterActive = new Text(genome.substring(activeGene + 1));
        return new HBox(beforeActive, active, afterActive);
    }
}
