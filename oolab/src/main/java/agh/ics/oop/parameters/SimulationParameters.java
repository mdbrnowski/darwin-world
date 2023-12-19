package agh.ics.oop.parameters;

import agh.ics.oop.parameters.EnergyParameters;
import agh.ics.oop.parameters.GeneralParameters;
import agh.ics.oop.parameters.MutationParameters;

public record SimulationParameters(GeneralParameters generalParameters, EnergyParameters energyParameters,
                                   MutationParameters mutationParameters) {
}
