package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GenomeTest {
    //FullPredestinationGenome
    @Test
    public void FullPredestinationGenomeIterateTest() {
        FullPredestinationGenome genome = new FullPredestinationGenome(List.of(0, 1, 2, 3, 4, 5, 6, 7, 0));
        List<Integer> correct = List.of(0, 1, 2, 3, 4, 5, 6, 7, 0, 0, 1, 2, 3, 4, 5, 6, 7, 0);
        int genomeSize = genome.getGenome().size();

        for (int i = 0; i < 2 * genomeSize; i++) {
            Assertions.assertEquals(genome.iterate(i), correct.get(i));
        }

    }

    @Test
    public void FullPredestinationGenomeEqualsTest() {
        FullPredestinationGenome genome1 = new FullPredestinationGenome(List.of(1, 2, 3, 4, 5, 6));
        FullPredestinationGenome genome2 = new FullPredestinationGenome(List.of(1, 2, 3, 4, 5, 6));
        FullPredestinationGenome genome3 = new FullPredestinationGenome(List.of(2, 2, 3, 0, 0, 6));
        FullPredestinationGenome genome4 = new FullPredestinationGenome(List.of(2, 2, 3, 0, 0, 6));

        Assertions.assertEquals(genome1, genome2);
        Assertions.assertEquals(genome3, genome4);
        Assertions.assertNotEquals(genome2, genome3);
    }

    //BackAndForthGenome
    @Test
    public void BackAndForthGenomeIterateTest() {
        BackAndForthGenome genome = new BackAndForthGenome(List.of(0, 1, 2, 3, 4, 5, 6, 7, 0));
        List<Integer> correct = List.of(0, 1, 2, 3, 4, 5, 6, 7, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0);
        int genomeSize = genome.getGenome().size();

        for (int i = 0; i < 2 * genomeSize; i++) {
            Assertions.assertEquals(genome.iterate(i), correct.get(i));
        }
    }

    @Test
    public void BackAndForthGenomeEqualsTest() {
        BackAndForthGenome genome1 = new BackAndForthGenome(List.of(1, 2, 3, 4, 5, 6));
        BackAndForthGenome genome2 = new BackAndForthGenome(List.of(1, 2, 3, 4, 5, 6));
        BackAndForthGenome genome3 = new BackAndForthGenome(List.of(2, 2, 3, 0, 0, 6));
        BackAndForthGenome genome4 = new BackAndForthGenome(List.of(2, 2, 3, 0, 0, 6));

        Assertions.assertEquals(genome1, genome2);
        Assertions.assertEquals(genome3, genome4);
        Assertions.assertNotEquals(genome2, genome3);
    }
}
