import agh.ics.oop.Simulation;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.EarthGlobe;
import agh.ics.oop.parameters.*;
import agh.ics.oop.parameters.types.GenomeType;
import agh.ics.oop.parameters.types.VegetationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimulationTest {
    @Test
    public void constructorTest() {
        GeneralParameters generalParameters = new GeneralParameters(GenomeType.FULL_PREDESTINATION_GENOME,
                3, VegetationType.FOREST_EQUATORS, 12, 10);
        EnergyParameters energyParameters = new EnergyParameters(3, 5,
                3, 1);
        MutationParameters mutationParameters = new MutationParameters("FullyRandom",
                2, 3);
        EarthGlobe map = new EarthGlobe(5, 7);
        SimulationParameters parameters = new SimulationParameters(generalParameters, energyParameters,
                mutationParameters);
        new Simulation(map, parameters);

        Assertions.assertEquals(map.getPlants().size(), 12);
        Assertions.assertEquals(map.getAnimals().size(), 10);

        for (Animal animal : map.getAnimals())
            Assertions.assertEquals(animal.getEnergy(), 5);
    }
}
