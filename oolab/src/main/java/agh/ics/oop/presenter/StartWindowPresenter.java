package agh.ics.oop.presenter;

import agh.ics.oop.model.util.InvalidParametersException;
import agh.ics.oop.parameters.*;
import agh.ics.oop.parameters.types.GenomeType;
import agh.ics.oop.parameters.types.MapType;
import agh.ics.oop.parameters.types.VegetationType;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

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
    @FXML
    public ComboBox<String> csvCombo;
    @FXML
    public Label errorLabel;
    private static final String PATH = "configurations.csv";
    private final Multimap<String, String> configurations = ArrayListMultimap.create();
    private List<Control> paramControls;
    private Stage primaryStage;

    public void initialize() {
        paramControls = List.of(mapCombo, widthSpinner, heightSpinner, genomeCombo, genomeLengthSpinner,
                vegetationCombo, plantsCountSpinner, animalsCountSpinner, plantsEnergySpinner, initialEnergySpinner,
                minimumBreedSpinner, childEnergySpinner, mutationTypeCombo, minMutationSpinner, maxMutationSpinner);
        String firstConf = null;

        try (Scanner scanner = new Scanner(Path.of(PATH))) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] params = line.split(";");
                firstConf = params[0];

                csvCombo.getItems().add(params[0]);
                for (int i = 1; i < params.length; i++) {
                    configurations.put(params[0], params[i]);
                }
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] params = line.split(";");
                csvCombo.getItems().add(params[0]);
                for (int i = 1; i < params.length; i++) {
                    configurations.put(params[0], params[i]);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (MapType mapEnum : MapType.values())
            mapCombo.getItems().add(mapEnum.toString());

        for (VegetationType vegetationEnum : VegetationType.values())
            vegetationCombo.getItems().add(vegetationEnum.toString());

        for (GenomeType genomeEnum : GenomeType.values())
            genomeCombo.getItems().add(genomeEnum.toString());

        populateStageParams(firstConf);

        csvCombo.setOnAction(event -> {
            String selected = csvCombo.getValue();
            populateStageParams(selected);
            csvCombo.getSelectionModel().select(selected);
        });
        removeAllConf();
    }


    private void removeAllConf() {
        for (Control control : paramControls) {
            if (control instanceof ComboBox) {
                ComboBox<String> combo = (ComboBox<String>) control;
                combo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                    csvCombo.getSelectionModel().select(null);
                });
            } else if (control instanceof Spinner) {
                Spinner<Integer> spinner = (Spinner<Integer>) control;
                spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
                    csvCombo.getSelectionModel().select(null);
                });
            }
        }
    }


    private void populateStageParams(String config) {
        List<String> params = new ArrayList<>(configurations.get(config));
        csvCombo.getSelectionModel().select(config);

        for (int i = 0; i < params.size(); i++) {
            Control control = paramControls.get(i);
            if (control instanceof ComboBox) {
                ComboBox<String> combo = (ComboBox<String>) control;
                combo.getSelectionModel().select(params.get(i));
            } else if (control instanceof Spinner) {
                Spinner<Integer> spinner = (Spinner<Integer>) control;
                spinner.getValueFactory().setValue(Integer.parseInt(params.get(i)));
            }
        }
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
        try {
            MapParameters mapParameters = new MapParameters(getMapTypeByDisplayValue(mapCombo.getValue()),
                    widthSpinner.getValue(), heightSpinner.getValue());
            SimulationParameters simulationParameters = getSimulationParameters();

            errorLabel.setText("");
            Stage newWindowStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulationWindow.fxml"));
            BorderPane viewRoot = loader.load();
            SimulationPresenter presenter = loader.getController();
            presenter.setStage(newWindowStage);
            configureStage(newWindowStage, viewRoot);
            newWindowStage.show();

            presenter.runSimulation(mapParameters, simulationParameters);

            newWindowStage.setOnCloseRequest(event -> presenter.shutdown());
        } catch (InvalidParametersException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private SimulationParameters getSimulationParameters() throws InvalidParametersException {
        GeneralParameters generalParameters = new GeneralParameters(
                getGenomeTypeByDisplayValue(genomeCombo.getValue()), genomeLengthSpinner.getValue(),
                getVegetationTypeByDisplayValue(vegetationCombo.getValue()), plantsCountSpinner.getValue(),
                animalsCountSpinner.getValue());

        EnergyParameters energyParameters = new EnergyParameters(plantsEnergySpinner.getValue(),
                initialEnergySpinner.getValue(), minimumBreedSpinner.getValue(), childEnergySpinner.getValue());

        MutationParameters mutationParameters = new MutationParameters(mutationTypeCombo.getValue(),
                minMutationSpinner.getValue(), maxMutationSpinner.getValue());

        ParametersValidator.validate(generalParameters, energyParameters, mutationParameters);

        return new SimulationParameters(generalParameters, energyParameters, mutationParameters);
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        this.primaryStage = primaryStage;
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    public void onSaveClicked(ActionEvent actionEvent) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Save configuration");

        VBox dialogVbox = new VBox(10);
        dialogVbox.getChildren().add(new Label("Type in configuration name:"));

        TextField textField = new TextField();
        textField.setPrefWidth(200);
        textField.setMaxWidth(200);
        dialogVbox.getChildren().add(textField);

        Label badSign = new Label("Your name cannot contain \";\"");
        Label nameExists = new Label("This name already exists");

        badSign.setVisible(false);
        badSign.setManaged(false);
        nameExists.setVisible(false);
        nameExists.setManaged(false);

        dialogVbox.getChildren().add(badSign);
        dialogVbox.getChildren().add(nameExists);

        Button cancel = new Button("Cancel");
        Button save = new Button("Save");

        cancel.setOnAction(event -> {
            dialog.close();
        });

        save.setOnAction(event -> {
            String name = textField.getText();
            if (name.contains(";")) {
                badSign.setVisible(true);
                badSign.setManaged(true);
                return;
            }
            if (!configurations.get(name).isEmpty()) {
                nameExists.setVisible(true);
                nameExists.setManaged(true);
                return;
            }
            writeCsvToFile(name);
            dialog.close();
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            badSign.setVisible(false);
            badSign.setManaged(false);
            nameExists.setVisible(false);
            nameExists.setManaged(false);
        });


        HBox buttonsHBox = new HBox(10);
        buttonsHBox.getChildren().add(cancel);
        buttonsHBox.getChildren().add(save);
        buttonsHBox.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().add(buttonsHBox);

        dialogVbox.setAlignment(Pos.CENTER);

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void writeCsvToFile(String name) {
        csvCombo.getItems().add(name);
        csvCombo.getSelectionModel().select(name);
        for (Control control : paramControls) {
            if (control instanceof ComboBox) {
                ComboBox<String> combo = (ComboBox<String>) control;
                configurations.put(name, combo.getValue());
            } else if (control instanceof Spinner) {
                Spinner<Integer> spinner = (Spinner<Integer>) control;
                configurations.put(name, spinner.getValue().toString());
            }
        }

        String csvData = name + ";" + String.join(";", configurations.get(name));
        try {
            Files.writeString(Path.of(PATH), csvData + System.lineSeparator(), CREATE, APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
