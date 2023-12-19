package agh.ics.oop.presenter;

import agh.ics.oop.SimulationParameters;
import agh.ics.oop.model.EnergyParameters;
import agh.ics.oop.model.GeneralParameters;
import agh.ics.oop.model.MapParameters;
import agh.ics.oop.model.MutationParameters;
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

    public void onSimulationStartClicked() throws IOException {
        Stage newWindowStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulationWindow.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();

        configureStage(newWindowStage, viewRoot);
        newWindowStage.show();

        MapParameters mapParameters = new MapParameters(mapCombo.getValue(), widthSpinner.getValue(),
                heightSpinner.getValue());

        SimulationParameters simulationParameters = getSimulationParameters();

        presenter.runSimulation(mapParameters, simulationParameters);
    }

    private SimulationParameters getSimulationParameters() {
        GeneralParameters generalParameters = new GeneralParameters(genomeCombo.getValue(),
                genomeLengthSpinner.getValue(), vegetationCombo.getValue(), plantsCountSpinner.getValue(),
                animalsCountSpinner.getValue());

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
