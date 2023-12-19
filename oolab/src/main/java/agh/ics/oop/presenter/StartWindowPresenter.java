package agh.ics.oop.presenter;

import agh.ics.oop.parameters.SimulationParameters;
import agh.ics.oop.parameters.EnergyParameters;
import agh.ics.oop.parameters.GeneralParameters;
import agh.ics.oop.parameters.MapParameters;
import agh.ics.oop.parameters.MutationParameters;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartWindowPresenter {
    @FXML
    public TextField argumentsInput;
    @FXML
    public ComboBox genomeCombo;
    @FXML
    public ComboBox mapCombo;
    @FXML
    public Spinner widthSpinner;
    @FXML
    public Spinner heightSpinner;
    @FXML
    public ComboBox vegetationCombo;
    @FXML
    public Spinner plantsCountSpinner;
    @FXML
    public Spinner animalsCountSpinner;
    @FXML
    public Spinner plantsEnergySpinner;
    @FXML
    public Spinner initialEnergySpinner;
    @FXML
    public Spinner minimumBreedSpinner;
    @FXML
    public Spinner childEnergySpinner;
    @FXML
    public ComboBox mutationTypeCombo;
    @FXML
    public Spinner minMutationSpinner;
    @FXML
    public Spinner maxMutationSpinner;
    @FXML
    public Spinner genomeLengthSpinner;

    public void onSimulationStartClicked() throws IOException {
        Stage newWindowStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulationWindow.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();

        configureStage(newWindowStage, viewRoot);
        newWindowStage.show();

        MapParameters mapParameters = new MapParameters((String) mapCombo.getValue(),
                (int) widthSpinner.getValue(), (int) heightSpinner.getValue());

        SimulationParameters simulationParameters = getSimulationParameters();

        presenter.runSimulation(mapParameters, simulationParameters);
    }

    private SimulationParameters getSimulationParameters() {
        GeneralParameters generalParameters = new GeneralParameters((String) genomeCombo.getValue(), (int) genomeLengthSpinner.getValue(),
                (String) vegetationCombo.getValue(), (int) plantsCountSpinner.getValue(), (int) animalsCountSpinner.getValue());


        EnergyParameters energyParameters = new EnergyParameters((int) plantsEnergySpinner.getValue(),
                (int) initialEnergySpinner.getValue(), (int) minimumBreedSpinner.getValue(), (int) childEnergySpinner.getValue());

        MutationParameters mutationParameters = new MutationParameters((String) mutationTypeCombo.getValue(),
                (int) minMutationSpinner.getValue(), (int) maxMutationSpinner.getValue());

        SimulationParameters simulationParameters = new SimulationParameters
                (generalParameters, energyParameters, mutationParameters);
        return simulationParameters;
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
