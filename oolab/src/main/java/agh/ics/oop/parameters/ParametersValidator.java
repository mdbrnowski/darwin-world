package agh.ics.oop.parameters;

public class ParametersValidator {
    public static void validate(GeneralParameters generalParameters, EnergyParameters energyParameters,
                                MutationParameters mutationParameters) throws InvalidParametersException {
        if (mutationParameters.minMutationNumber() > mutationParameters.maxMutationNumber())
            throw new InvalidParametersException("Minimum mutation number should be smaller than its maximum.");

        if (generalParameters.genomeLength() <= mutationParameters.maxMutationNumber())
            throw new InvalidParametersException("Genome length should be larger than maximum mutation number.");

        if (energyParameters.minBreedEnergy() <= energyParameters.energyForChild())
            throw new InvalidParametersException("Energy for child should be smaller than minimum breed energy.");
    }
}
