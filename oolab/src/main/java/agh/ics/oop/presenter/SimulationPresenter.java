package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.onActionControls.Pause;
import agh.ics.oop.parameters.SimulationParameters;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.PopularityCounter;
import agh.ics.oop.parameters.MapParameters;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap map;
    @FXML
    public Button highlightPreferredButton;
    @FXML
    public Button highlightGenomeButton;
    @FXML
    public Label plantsEatenLabel;
    @FXML
    public Label animalAgeLabelTitle;
    @FXML
    public GridPane trackPanel;
    @FXML
    public Label idLabel;
    @FXML
    public Label animalGenomeLabel;
    @FXML
    public Label animalEnergyLabel;
    @FXML
    public Label animalChildrenNumLabel;
    @FXML
    public Label animalAgeLabel;
    @FXML
    public Button pauseButton;
    @FXML
    public ImageView pauseButtonImageView;
    @FXML
    public GridPane mapGrid;

    @FXML
    private Label moveDescriptionLabel;

    @FXML
    public GridPane statsPanel;
    @FXML
    public Label numberOfAnimalsLabel;
    @FXML
    public Label numberOfPlantsLabel;
    @FXML
    public Label numberOfEmptyFieldsLabel;
    @FXML
    public Label averageLifeSpanLabel;
    @FXML
    public Label averageEnergyLabel;
    @FXML
    public Label averageNumberOfChildrenLabel;
    @FXML
    public Label mostPopularGenotypeLabel;

    private SimulationEngine simulationEngine;
    private Simulation simulation;
    private Stage stage;
    private Pause pause = new Pause();
    private HashMap<Vector2d, Double> highlightGenomePositions = new HashMap<>();
    private boolean highlightGenomeButtonPressed = false;
    private boolean highlightPreferredButtonPressed = false;
    private List<Vector2d> highlightPreferred = new ArrayList<>();

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            updateStats();
            drawMap();
            if (pause.isTracked()) {
                trackPanel.setVisible(true);
                trackPanel.setManaged(true);
                updateTrackedAnimal(pause.getTrackedAnimal());
            } else {
                trackPanel.setVisible(false);
                trackPanel.setManaged(false);
            }
            moveDescriptionLabel.setText(message);
        });
    }

    public void stopTracking() {
        pause.stopTracking();
        trackPanel.setVisible(false);
        trackPanel.setManaged(false);
    }

    private void setWorldMap(WorldMap map) {
        this.map = map;
        drawMap();
    }

    public void setupStats() {
        for (Node node : statsPanel.getChildren())
            if (node instanceof Label label) {
                if (GridPane.getColumnIndex(node) == 1)
                    label.setFont(new Font(14));
                else
                    label.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 15));
            }
    }

    public void updateStats() {
        var animals = map.getAnimals();
        var deadAnimals = map.getDeadAnimals();
        var plants = map.getPlants();
        numberOfAnimalsLabel.setText(String.valueOf(animals.size()));
        numberOfPlantsLabel.setText(String.valueOf(plants.size()));
        numberOfEmptyFieldsLabel.setText(String.valueOf(map.getNumberOfEmptyFields()));
        double averageLifeSpan = deadAnimals.stream().collect(Collectors.averagingDouble(Animal::getAge));
        averageLifeSpanLabel.setText(averageLifeSpan > 0 ? String.format("%.1f", averageLifeSpan) : "N/A");
        averageEnergyLabel.setText(String.format("%.1f",
                animals.stream().collect(Collectors.averagingDouble(Animal::getEnergy))));
        averageNumberOfChildrenLabel.setText(String.format("%.1f",
                animals.stream().collect(Collectors.averagingDouble(Animal::getChildrenNum))));
        mostPopularGenotypeLabel.setText(
                PopularityCounter.getMostPopularAsString(animals.stream()
                        .map(a -> a.getGenome().toString())
                        .collect(Collectors.toList()), 3));
    }

    public void updateTrackedAnimal(Animal trackedAnimal) {
        if (trackedAnimal.getEnergy() <= 0 && trackedAnimal.getDiedOn().isPresent()) {
            animalAgeLabelTitle.setText("Died on day: ");
            animalAgeLabel.setText(String.format("%d", trackedAnimal.getDiedOn().get()));
        } else {
            animalAgeLabel.setText(String.format("%d", trackedAnimal.getAge()));
            animalGenomeLabel.setGraphic(pause.getGenomeLabelContent(trackedAnimal, map.getDay()));
        }
        idLabel.setText(String.valueOf(trackedAnimal.getId()));
        animalEnergyLabel.setText(String.format("%d", trackedAnimal.getEnergy()));
        animalChildrenNumLabel.setText(String.format("%d", trackedAnimal.getChildrenNum()));
        plantsEatenLabel.setText(String.format("%d", trackedAnimal.getPlantsEaten()));

    }

    public void drawMap() {
        clearGrid();
        Boundary boundary = map.getCurrentBounds();
        int minX = boundary.bottomLeft().getX(), minY = boundary.bottomLeft().getY();
        int maxX = boundary.topRight().getX(), maxY = boundary.topRight().getY();
        mapGrid.add(new Label("y\\x"), 0, 0);
        for (int x = minX; x <= maxX; x++)
            mapGrid.add(new Label("%d".formatted(x)), x - minX + 1, 0);
        for (int y = minY; y <= maxY; y++)
            mapGrid.add(new Label("%d".formatted(y)), 0, maxY - y + 1);

        for (int i = 0; i < maxX - minX + 2; i++)
            mapGrid.getColumnConstraints().add(new ColumnConstraints(40));
        for (int i = 0; i < maxY - minY + 2; i++)
            mapGrid.getRowConstraints().add(new RowConstraints(40));


        updateStats();
        int maxEnergy = map.getAnimals().stream().mapToInt(Animal::getEnergy).max().orElse(1);
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                var label = new Label();
                var animals = map.getAnimalsAt(new Vector2d(x, y));
                label.setPrefHeight(38);
                label.setPrefWidth(38);
                label.setAlignment(Pos.CENTER);

                if (simulation != null && simulation.isStopped()) {
                    if (!animals.isEmpty()) label.setOnMouseClicked(a -> {
                        pause.showAnimalStats(label, animals, stage, map.getDay());
                    });
                    if (highlightGenomePositions.get(new Vector2d(x, y)) != null) {
                        label.setStyle(String.format("-fx-background-color: rgba(255,240,%.2f,0.8)",
                                255 - highlightGenomePositions.get(new Vector2d(x, y)) * 255));
                    }
                    if (highlightPreferred.contains(new Vector2d(x, y))) {
                        label.setStyle("-fx-background-color: rgba(20,230,0,0.5)");
                    }
                }

                if (pause.isTracked() && pause.getTrackedAnimal().getPosition().equals(new Vector2d(x, y))) {
                    BorderStroke borderStroke = new BorderStroke(
                            Color.BLUE,
                            BorderStrokeStyle.SOLID,
                            null,
                            new BorderWidths(2)
                    );

                    Border border = new Border(borderStroke);
                    label.setBorder(border);
                }
                if (animals.size() > 1) {
                    var animal = Collections.max(animals);
                    label.setText(Animal.MULTIPLE_ANIMALS_TO_STRING);
                    label.setTextFill(Color.color((double) animal.getEnergy() / maxEnergy, 0, 0));
                    label.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 24));
                } else if (animals.size() == 1) {
                    var animal = animals.get(0);
                    label.setText(animal.toString());
                    label.setTextFill(Color.color((double) animal.getEnergy() / maxEnergy, 0, 0));
                    label.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 24));
                } else if (map.getPlantAt(new Vector2d(x, y)) != null) {
                    label.setText(map.getPlantAt(new Vector2d(x, y)).toString());
                    label.setTextFill(Color.color(0.2, 0.6, 0.3));
                    label.setFont(Font.font("Arial", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 16));
                }
                mapGrid.add(label, x - minX + 1, maxY - y + 1);
            }
        }


        for (Node label : mapGrid.getChildren())
            GridPane.setHalignment(label, HPos.CENTER);

    }

    public void highlightGenome() {
        if (!highlightGenomeButtonPressed) {
            highlightGenomeButtonPressed = true;
            highlightGenomePositions = pause.highlightGenomes(map);
            highlightGenomeButton.setStyle("-fx-background-color: rgba(245,204,27,0.63);-fx-border-color: lightblue;-fx-border-radius: 5px");
        } else {
            highlightGenomeButtonPressed = false;
            highlightGenomePositions.clear();
            highlightGenomeButton.setStyle(null);
        }
        drawMap();
    }

    public void highlightPreferred() {
        if (!highlightPreferredButtonPressed) {
            highlightPreferredButtonPressed = true;
            highlightPreferred = pause.highlightPreferred(simulation.getVegetation(), map);
            highlightPreferredButton.setStyle("-fx-background-color: rgba(20,230,0,0.5);-fx-border-color: lightblue;-fx-border-radius: 5px");
        } else {
            highlightPreferredButtonPressed = false;
            highlightPreferred.clear();
            highlightPreferredButton.setStyle(null);
        }
        drawMap();
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void runSimulation(MapParameters mapParameters, SimulationParameters simulationParameters) {
        AbstractWorldMap map = mapParameters.mapType().getEquivalentObject(mapParameters.mapWidth(),
                mapParameters.mapHeight());
        setWorldMap(map);
        map.addObserver(this);
        simulation = new Simulation(map, simulationParameters, 500);
        simulationEngine = new SimulationEngine(List.of(simulation));
        simulationEngine.runAsync();
        setupStats();
        System.out.println(this);
    }

    public void onPauseButtonClicked() {
        highlightGenomePositions.clear();
        highlightPreferred.clear();
        highlightGenomeButton.setStyle(null);
        highlightPreferredButton.setStyle(null);
        highlightGenomeButtonPressed = false;
        highlightPreferredButtonPressed = false;
        Pause.pause(simulation, pauseButtonImageView, highlightGenomeButton, highlightPreferredButton);
        drawMap();
    }

    public void shutdown() {
        simulationEngine.shutdown();
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
