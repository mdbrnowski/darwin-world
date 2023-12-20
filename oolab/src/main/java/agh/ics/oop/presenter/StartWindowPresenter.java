package agh.ics.oop.presenter;

import agh.ics.oop.parameters.*;
import agh.ics.oop.parameters.enums.GenomeEnum;
import agh.ics.oop.parameters.enums.MapEnum;
import agh.ics.oop.parameters.enums.VegetationEnum;
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
        for (MapEnum mapEnum : MapEnum.values()) {
            mapCombo.getItems().add(mapEnum.getDisplayValue());
        }
        mapCombo.getSelectionModel().select(MapEnum.EARTHGLOBE.getDisplayValue());

        for (VegetationEnum vegetationEnum : VegetationEnum.values()) {
            vegetationCombo.getItems().add(vegetationEnum.getDisplayValue());
        }
        vegetationCombo.getSelectionModel().select(VegetationEnum.FORESTEQUATORS.getDisplayValue());

        for (GenomeEnum genomeEnum : GenomeEnum.values()) {
            genomeCombo.getItems().add(genomeEnum.getDisplayValue());
        }
        genomeCombo.getSelectionModel().select(GenomeEnum.FULLPREDESTINATIONGENOME.getDisplayValue());
    }

    private MapEnum getMapEnumByDisplayValue(String displayValue) {
        for (MapEnum mapEnum : MapEnum.values()) {
            if (mapEnum.getDisplayValue().equals(displayValue)) {
                return mapEnum;
            }
        }
        throw new IllegalArgumentException();
    }

    private VegetationEnum getVegetationEnumByDisplayValue(String displayValue) {
        for (VegetationEnum vegetation : VegetationEnum.values()) {
            if (vegetation.getDisplayValue().equals(displayValue)) {
                return vegetation;
            }
        }
        throw new IllegalArgumentException();
    }

    private GenomeEnum getGenomeEnumByDisplayValue(String displayValue) {
        for (GenomeEnum genome : GenomeEnum.values()) {
            if (genome.getDisplayValue().equals(displayValue)) {
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

        MapParameters mapParameters = new MapParameters(getMapEnumByDisplayValue(mapCombo.getValue()), widthSpinner.getValue(),
                heightSpinner.getValue());

        SimulationParameters simulationParameters = getSimulationParameters();

        presenter.runSimulation(mapParameters, simulationParameters);
    }

    private SimulationParameters getSimulationParameters() {
        GeneralParameters generalParameters = new GeneralParameters(getGenomeEnumByDisplayValue(genomeCombo.getValue()),
                genomeLengthSpinner.getValue(), getVegetationEnumByDisplayValue(vegetationCombo.getValue()),
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
