package agh.ics.oop.presenter;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap map;
    @FXML
    private Label infoLabel;

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        drawMap();
    }

    public void setWorldMap(WorldMap map) {
        this.map = map;
    }

    public void drawMap() {
        infoLabel.setText(map.toString());
    }
}
