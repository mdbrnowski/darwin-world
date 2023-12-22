package agh.ics.oop.parameters;

import agh.ics.oop.parameters.types.GenomeType;
import agh.ics.oop.parameters.types.VegetationType;

public record GeneralParameters(GenomeType genome, int genomeLength, VegetationType vegetation, int startPlantsCount,
                                int startAnimalsCount) {
}
