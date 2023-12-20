package agh.ics.oop.parameters;

import agh.ics.oop.parameters.enums.GenomeEnum;
import agh.ics.oop.parameters.enums.VegetationEnum;

public record GeneralParameters(GenomeEnum genome, int genomeLength, VegetationEnum vegetation, int startPlantsCount,
                                int startAnimalsCount) {
}
