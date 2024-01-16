package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.AbstractWorldMap;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.PopularityCounter;
import agh.ics.oop.onActionControls.Pause;
import agh.ics.oop.parameters.MapParameters;
import agh.ics.oop.parameters.SimulationParameters;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimulationPresenter implements MapChangeListener {
    private AbstractWorldMap map;
    @FXML
    public Slider speedSlider;
    @FXML
    public Button speedDownButton;
    @FXML
    public Button speedUpButton;
    @FXML
    public Label descendantsNumLabel;
    @FXML
    public ScrollPane mapScrollPane;
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
    private final Pause pause = new Pause();
    private HashMap<Vector2d, Double> highlightGenomePositions = new HashMap<>();
    private boolean highlightGenomeButtonPressed = false;
    private boolean highlightPreferredButtonPressed = false;
    private Set<Vector2d> highlightPreferred = new HashSet<>();
    private boolean logging;
    private String logging_path;

    @Override
    public void mapChanged(AbstractWorldMap map, String message) {
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
            speedUpButton.setDisable(simulation.getSleepTime() <= 100);
            speedDownButton.setDisable(simulation.getSleepTime() >= 2000);
            moveDescriptionLabel.setText(message);
        });
    }

    public void stopTracking() {
        pause.stopTracking();
        trackPanel.setVisible(false);
        trackPanel.setManaged(false);
    }

    private void setWorldMap(AbstractWorldMap map) {
        this.map = map;
        drawMap();
    }

    public void setupStats() {
        logging_path = "log_%s.csv".formatted(simulation.toString().substring(
                simulation.toString().length() - 7));
        for (Node node : statsPanel.getChildren()) {
            if (node instanceof Label label) {
                if (GridPane.getColumnIndex(node) == 1)
                    label.setFont(new Font(14));
                else
                    label.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 15));
            }
        }
        if (logging) {
            try {
                List<String> headers = List.of("animals", "plants", "avg life span", "avg energy",
                        "avg number of children");
                Files.writeString(Path.of(logging_path), String.join(",", headers) + System.lineSeparator(),
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        double averageEnergy = animals.stream().collect(Collectors.averagingDouble(Animal::getEnergy));
        averageEnergyLabel.setText(String.format("%.1f", averageEnergy));
        double averageNumberOfChildren = animals.stream().collect(Collectors.averagingDouble(Animal::getChildrenNum));
        averageNumberOfChildrenLabel.setText(String.format("%.1f", averageNumberOfChildren));
        mostPopularGenotypeLabel.setText(
                PopularityCounter.getMostPopularAsString(animals.stream()
                        .map(a -> a.getGenome().toString())
                        .collect(Collectors.toList()), 3));
        if (logging) {
            try {
                var stats = Stream.of(animals.size(), plants.size(), averageLifeSpan, averageEnergy,
                        averageNumberOfChildren).map(Object::toString).collect(Collectors.toList());
                Files.writeString(Path.of(logging_path), String.join(",", stats) + System.lineSeparator(),
                        StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateTrackedAnimal(Animal trackedAnimal) {
        if (trackedAnimal.getDiedOn().isPresent()) {
            animalAgeLabelTitle.setText("Died on day: ");
            animalAgeLabel.setText(String.format("%d", trackedAnimal.getDiedOn().get()));
        } else {
            animalAgeLabelTitle.setText("Age: ");
            animalAgeLabel.setText(String.format("%d", trackedAnimal.getAge()));
            animalGenomeLabel.setGraphic(pause.getGenomeLabelContent(trackedAnimal, map.getDay()));
            animalGenomeLabel.setGraphic(pause.getGenomeLabelContent(trackedAnimal, map.getDay()));
        }
        idLabel.setText(String.valueOf(trackedAnimal.getId()));
        animalEnergyLabel.setText(String.format("%d", trackedAnimal.getEnergy()));
        animalChildrenNumLabel.setText(String.format("%d", trackedAnimal.getChildrenNum()));
        plantsEatenLabel.setText(String.format("%d", trackedAnimal.getPlantsEaten()));
        descendantsNumLabel.setText(String.format("%d", trackedAnimal.getDescendantsNum()));
    }

    public void drawMap() {
        clearGrid();
        Boundary boundary = map.getCurrentBounds();
        int minX = boundary.bottomLeft().x(), minY = boundary.bottomLeft().y();
        int maxX = boundary.topRight().x(), maxY = boundary.topRight().y();

        configureMapGrid(minX, maxX, minY, maxY);
        fillMapGridWithElements(minX, maxX, minY, maxY);

        for (Node label : mapGrid.getChildren())
            GridPane.setHalignment(label, HPos.CENTER);

    }

    public void fillMapGridWithElements(int minX, int maxX, int minY, int maxY) {
        int maxEnergy = map.getAnimals().stream().mapToInt(Animal::getEnergy).max().orElse(1);
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                var label = new Label();
                var animals = map.getAnimalsAt(new Vector2d(x, y));
                label.setPrefHeight(38);
                label.setPrefWidth(38);
                label.setAlignment(Pos.CENTER);

                specialFieldAppearance(label, animals, x, y);

                if (animals.size() > 1) {
                    var animal = Collections.max(animals);
                    label.setText(Animal.MULTIPLE_ANIMALS_TO_STRING);
                    label.setTextFill(Color.color((double) animal.getEnergy() / maxEnergy, 0, 0));
                    label.getStyleClass().add("mapAnimalLabel");
                } else if (animals.size() == 1) {
                    var animal = animals.get(0);
                    label.setText(animal.toString());
                    label.setTextFill(Color.color((double) animal.getEnergy() / maxEnergy, 0, 0));
                    label.getStyleClass().add("mapAnimalLabel");
                } else if (map.getPlantAt(new Vector2d(x, y)) != null) {
                    label.setText(map.getPlantAt(new Vector2d(x, y)).toString());
                    label.setTextFill(Color.color(0.2, 0.6, 0.3));
                    label.getStyleClass().add("mapGrassLabel");
                }
                mapGrid.add(label, x - minX + 1, maxY - y + 1);
            }
        }
    }

    public void configureMapGrid(int minX, int maxX, int minY, int maxY) {
        mapScrollPane.setMinWidth(Math.min((maxX - minX + 2) * 40 + 50, Screen.getPrimary().getBounds().getWidth() - 150));
        mapScrollPane.setPrefHeight(Math.min((maxY - minY + 2) * 40 + 50, Screen.getPrimary().getBounds().getHeight() - 150));
        mapGrid.add(new Label("y\\x"), 0, 0);
        for (int x = minX; x <= maxX; x++)
            mapGrid.add(new Label("%d".formatted(x)), x - minX + 1, 0);
        for (int y = minY; y <= maxY; y++)
            mapGrid.add(new Label("%d".formatted(y)), 0, maxY - y + 1);

        for (int i = 0; i < maxX - minX + 2; i++)
            mapGrid.getColumnConstraints().add(new ColumnConstraints(40));
        for (int i = 0; i < maxY - minY + 2; i++)
            mapGrid.getRowConstraints().add(new RowConstraints(40));
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void specialFieldAppearance(Label label, List<Animal> animals, int x, int y) {
        if (simulation != null && simulation.isStopped()) {
            if (!animals.isEmpty()) label.setOnMouseClicked(a ->
                    pause.showAnimalStats(label, animals, stage, map.getDay()));
            if (highlightGenomePositions.get(new Vector2d(x, y)) != null) {
                label.setStyle(String.format("-fx-background-color: rgba(255,240,%.2f,0.8)",
                        255 - highlightGenomePositions.get(new Vector2d(x, y)) * 255));
            }
            if (highlightPreferred.contains(new Vector2d(x, y)))
                label.getStyleClass().add("preferredMapLabel");
        }

        if (pause.isTracked() && pause.getTrackedAnimal().getPosition().equals(new Vector2d(x, y)) &&
                pause.getTrackedAnimal().getDiedOn().isEmpty()) {
            label.getStyleClass().add("trackBoarder");
        }
    }

    public void highlightGenome() {
        if (!highlightGenomeButtonPressed) {
            highlightGenomeButtonPressed = true;
            highlightGenomePositions = pause.highlightGenomes(map);
            highlightGenomeButton.getStyleClass().remove("buttonNotClicked");
            highlightGenomeButton.getStyleClass().add("highlightGenomeClicked");
        } else {
            highlightGenomeButtonPressed = false;
            highlightGenomePositions.clear();
            highlightGenomeButton.getStyleClass().remove("highlightGenomeClicked");
            highlightGenomeButton.getStyleClass().add("buttonNotClicked");
        }
        drawMap();
    }

    public void highlightPreferred() {
        if (!highlightPreferredButtonPressed) {
            highlightPreferredButtonPressed = true;
            highlightPreferred = simulation.getVegetation().getPreferred(map);
            highlightPreferredButton.getStyleClass().remove("buttonNotClicked");
            highlightPreferredButton.getStyleClass().add("highlightPreferredClicked");
        } else {
            highlightPreferredButtonPressed = false;
            highlightPreferred.clear();
            highlightPreferredButton.getStyleClass().remove("highlightPreferredClicked");
            highlightPreferredButton.getStyleClass().add("buttonNotClicked");
        }
        drawMap();
    }

    public void runSimulation(MapParameters mapParameters, SimulationParameters simulationParameters, boolean logging) {
        AbstractWorldMap map = mapParameters.mapType().getEquivalentObject(mapParameters.mapWidth(),
                mapParameters.mapHeight());
        setWorldMap(map);
        map.addObserver(this);
        this.logging = logging;
        simulation = new Simulation(map, simulationParameters, (int) (speedSlider.getMax() + 100 - speedSlider.getValue()));
        simulationEngine = new SimulationEngine(List.of(simulation));
        simulationEngine.runAsync();
        setupStats();
        System.out.println(this);
    }

    public void onPauseButtonClicked() {
        highlightGenomePositions.clear();
        highlightPreferred.clear();
        highlightGenomeButton.getStyleClass().remove("highlightGenomeClicked");
        highlightGenomeButton.getStyleClass().add("buttonNotClicked");
        highlightPreferredButton.getStyleClass().remove("highlightPreferredClicked");
        highlightPreferredButton.getStyleClass().add("buttonNotClicked");
        highlightGenomeButtonPressed = false;
        highlightPreferredButtonPressed = false;
        Pause.pause(simulation, pauseButtonImageView, highlightGenomeButton, highlightPreferredButton);
        drawMap();
    }


    public void onSpeedDownClicked() {
        simulation.increaseSleepTime();
        speedSlider.setValue(speedSlider.getMax() - simulation.getSleepTime());
    }

    public void onSpeedUpClicked() {
        simulation.decreaseSleepTime();
        speedSlider.setValue(speedSlider.getMax() - simulation.getSleepTime());
    }

    public void onSliderChanged() {
        simulation.setSleepTime((int) (speedSlider.getMax() + 100 - speedSlider.getValue()));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void shutdown() {
        simulationEngine.shutdown();
    }
}
