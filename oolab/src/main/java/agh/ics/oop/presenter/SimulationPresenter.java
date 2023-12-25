package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.parameters.SimulationParameters;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.parameters.MapParameters;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap map;

    @FXML
    public GridPane mapGrid;
    @FXML
    private Label moveDescriptionLabel;


    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            moveDescriptionLabel.setText(message);
        });
    }

    public void setWorldMap(WorldMap map) {
        this.map = map;
        drawMap();
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

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                var label = new Label();
                if (map.getAnimalsAt(new Vector2d(x, y)).size() > 1) {
                    label.setText(Animal.MULTIPLE_ANIMALS_TO_STRING);
                    label.setTextFill(Color.color(1, 0, 0));
                } else if (map.getAnimalsAt(new Vector2d(x, y)).size() == 1) {
                    label.setText(map.getAnimalsAt(new Vector2d(x, y)).get(0).toString());
                    label.setTextFill(Color.color(1, 0, 0));
                } else if (map.getPlantAt(new Vector2d(x, y)) != null) {
                    label.setText(map.getPlantAt(new Vector2d(x, y)).toString());
                    label.setTextFill(Color.color(0.2, 0.6, 0.3));
                }
                label.setFont(Font.font(20));
                mapGrid.add(label, x - minX + 1, maxY - y + 1);
            }
        }

        for (Node label : mapGrid.getChildren())
            GridPane.setHalignment(label, HPos.CENTER);
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
        Simulation simulation = new Simulation(map, simulationParameters, 500);
        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));
        simulationEngine.runAsync();
    }
}
