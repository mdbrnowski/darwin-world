package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.Boundary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;
import java.util.Objects;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap map;

    @FXML
    public GridPane mapGrid;
    @FXML
    private Label moveDescriptionLabel;
    @FXML
    public TextField directionsTextField;


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
                if (map.objectAt(new Vector2d(x, y)) != null) {
                    var label = new Label(map.objectAt(new Vector2d(x, y)).toString());
                    if (!Objects.equals(label.getText(), "*"))
                        label.setTextFill(Color.color(1, 0, 0));
                    else
                        label.setTextFill(Color.color(0.2, 0.6, 0.3));
                    label.setFont(Font.font(20));
                    mapGrid.add(label, x - minX + 1, maxY - y + 1);
                }
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

    public void onSimulationStartClicked() {
        List<Vector2d> positions = List.of(new Vector2d(1, 1), new Vector2d(3, 2));

        Thread simulationThread = new Thread(() -> {
            Simulation simulation = new Simulation(map, positions, 500);
            SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));
            simulationEngine.runAsync();
        });
        simulationThread.start();
    }
}
