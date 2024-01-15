package agh.ics.oop.parameters;

import agh.ics.oop.parameters.types.GenomeType;
import agh.ics.oop.parameters.types.MapType;
import agh.ics.oop.parameters.types.VegetationType;
import agh.ics.oop.presenter.StartWindowPresenter;

public class ParametersHandler {
    private final StartWindowPresenter presenter;

    public ParametersHandler(StartWindowPresenter presenter) {
        this.presenter = presenter;
    }

    private MapType getMapTypeByDisplayValue(String displayValue) {
        for (MapType mapEnum : MapType.values())
            if (mapEnum.toString().equals(displayValue))
                return mapEnum;
        throw new IllegalArgumentException();
    }

    private VegetationType getVegetationTypeByDisplayValue(String displayValue) {
        for (VegetationType vegetation : VegetationType.values())
            if (vegetation.toString().equals(displayValue))
                return vegetation;
        throw new IllegalArgumentException();
    }

    private GenomeType getGenomeTypeByDisplayValue(String displayValue) {
        for (GenomeType genome : GenomeType.values())
            if (genome.toString().equals(displayValue))
                return genome;
        throw new IllegalArgumentException();
    }

    public MapParameters getMapParameters() {
        return new MapParameters(getMapTypeByDisplayValue(presenter.mapCombo.getValue()),
                presenter.widthSpinner.getValue(), presenter.heightSpinner.getValue());
    }

    public SimulationParameters getSimulationParameters() throws InvalidParametersException {
        GeneralParameters generalParameters = new GeneralParameters(
                getGenomeTypeByDisplayValue(presenter.genomeCombo.getValue()), presenter.genomeLengthSpinner.getValue(),
                getVegetationTypeByDisplayValue(presenter.vegetationCombo.getValue()),
                presenter.plantsCountSpinner.getValue(), presenter.animalsCountSpinner.getValue());

        EnergyParameters energyParameters = new EnergyParameters(presenter.plantsEnergySpinner.getValue(),
                presenter.initialEnergySpinner.getValue(), presenter.minimumBreedSpinner.getValue(),
                presenter.childEnergySpinner.getValue());

        MutationParameters mutationParameters = new MutationParameters(presenter.mutationTypeCombo.getValue(),
                presenter.minMutationSpinner.getValue(), presenter.maxMutationSpinner.getValue());

        ParametersValidator.validate(generalParameters, energyParameters, mutationParameters);

        return new SimulationParameters(generalParameters, energyParameters, mutationParameters);
    }
}
