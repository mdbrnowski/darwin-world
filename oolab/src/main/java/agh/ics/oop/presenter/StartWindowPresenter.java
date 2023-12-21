package agh.ics.oop.presenter;

import agh.ics.oop.parameters.*;
import agh.ics.oop.parameters.types.GenomeType;
import agh.ics.oop.parameters.types.MapType;
import agh.ics.oop.parameters.types.VegetationType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartWindowPresenter {
    @FXML
    public ComboBox<String> genomeCombo;
    @FXML
    public ComboBox<String> mapCombo;
    @FXML
    public Spinner<Integer> widthSpinner;
    @FXML
    public Spinner<Integer> heightSpinner;
    @FXML
    public ComboBox<String> vegetationCombo;
    @FXML
    public Spinner<Integer> plantsCountSpinner;
    @FXML
    public Spinner<Integer> animalsCountSpinner;
    @FXML
    public Spinner<Integer> plantsEnergySpinner;
    @FXML
    public Spinner<Integer> initialEnergySpinner;
    @FXML
    public Spinner<Integer> minimumBreedSpinner;
    @FXML
    public Spinner<Integer> childEnergySpinner;
    @FXML
    public ComboBox<String> mutationTypeCombo;
    @FXML
    public Spinner<Integer> minMutationSpinner;
    @FXML
    public Spinner<Integer> maxMutationSpinner;
    @FXML
    public Spinner<Integer> genomeLengthSpinner;

    public void initialize() {
        for (MapType mapEnum : MapType.values()) {
            mapCombo.getItems().add(mapEnum.toString());
        }
        mapCombo.getSelectionModel().select(MapType.EARTH_GLOBE.toString());

        for (VegetationType vegetationEnum : VegetationType.values()) {
            vegetationCombo.getItems().add(vegetationEnum.toString());
        }
        vegetationCombo.getSelectionModel().select(VegetationType.FOREST_EQUATORS.toString());

        for (GenomeType genomeEnum : GenomeType.values()) {
            genomeCombo.getItems().add(genomeEnum.toString());
        }
        genomeCombo.getSelectionModel().select(GenomeType.FULL_PREDESTINATION_GENOME.toString());
    }

    private MapType getMapTypeByDisplayValue(String displayValue) {
        for (MapType mapEnum : MapType.values()) {
            if (mapEnum.toString().equals(displayValue)) {
                return mapEnum;
            }
        }
        throw new IllegalArgumentException();
    }

    private VegetationType getVegetationTypeByDisplayValue(String displayValue) {
        for (VegetationType vegetation : VegetationType.values()) {
            if (vegetation.toString().equals(displayValue)) {
                return vegetation;
            }
        }
        throw new IllegalArgumentException();
    }

    private GenomeType getGenomeTypeByDisplayValue(String displayValue) {
        for (GenomeType genome : GenomeType.values()) {
            if (genome.toString().equals(displayValue)) {
                return genome;
            }
        }
        throw new IllegalArgumentException();
    }

    public void onSimulationStartClicked() throws IOException {
        Stage newWindowStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulationWindow.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();

        configureStage(newWindowStage, viewRoot);
        newWindowStage.show();

        MapParameters mapParameters = new MapParameters(getMapTypeByDisplayValue(mapCombo.getValue()),
                widthSpinner.getValue(), heightSpinner.getValue());

        SimulationParameters simulationParameters = getSimulationParameters();

        presenter.runSimulation(mapParameters, simulationParameters);
    }

    private SimulationParameters getSimulationParameters() {
        GeneralParameters generalParameters = new GeneralParameters(getGenomeTypeByDisplayValue(genomeCombo.getValue()),
                genomeLengthSpinner.getValue(), getVegetationTypeByDisplayValue(vegetationCombo.getValue()),
                plantsCountSpinner.getValue(), animalsCountSpinner.getValue());

        EnergyParameters energyParameters = new EnergyParameters(plantsEnergySpinner.getValue(),
                initialEnergySpinner.getValue(), minimumBreedSpinner.getValue(), childEnergySpinner.getValue());

        MutationParameters mutationParameters = new MutationParameters(mutationTypeCombo.getValue(),
                minMutationSpinner.getValue(), maxMutationSpinner.getValue());

        return new SimulationParameters(generalParameters, energyParameters, mutationParameters);
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
