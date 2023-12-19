package agh.ics.oop;

import agh.ics.oop.model.EnergyParameters;
import agh.ics.oop.model.GeneralParameters;
import agh.ics.oop.model.MutationParameters;

public record SimulationParameters(GeneralParameters generalParameters, EnergyParameters energyParameters,
                                   MutationParameters mutationParameters) {
}
