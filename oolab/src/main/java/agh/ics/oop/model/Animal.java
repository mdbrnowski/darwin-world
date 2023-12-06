package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.floor;
import static java.lang.Math.round;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;
    private int energy;
    private int childrenNum;
    private int age;
    private AbstractGenome genome;



    public Animal(Vector2d position,MapDirection orientation,AbstractGenome genome) {
        this.orientation = orientation;
        this.genome=genome;
        this.position = position;
        this.childrenNum=0;
        this.age=0;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    @Override
    public String toString() {
        return orientation.toString();
    }

    public void move(MoveValidator validator,Vector2d new_position) {
//        Vector2d new_position = position.add(orientation.toUnitVector());
        if (validator.canMoveTo(new_position))
            position = new_position;
    }

    public void incrementEnergy(int value){
        energy+=value;
    }

    public int getEnergy() {
        return energy;
    }

    public AbstractGenome getGenome() {
        return genome;
    }

    public void setGenome(AbstractGenome genome) {
        this.genome = genome;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setOrientation(MapDirection orientation) {
        this.orientation = orientation;
    }

    public Animal breed(Animal animal, int minMutations, int maxMutations){
        double energyPart= ((double)this.energy) /((double)(animal.getEnergy()+this.energy));
        int genomeSize=genome.genome.size();
        int firstGenome= (int) floor(energyPart*genomeSize);


        List<Integer> genomeList=new ArrayList<>();

        for(int i=0;i<=firstGenome;i++){
            genomeList.add(genome.genome.get(i));
        }

        for(int i=firstGenome+1;i<genomeSize;i++){
            genomeList.add(animal.getGenome().genome.get(i));
        }


        Random random=new Random();
        int mutationNumber=random.nextInt((maxMutations-minMutations))+minMutations;

        RandomGenerator randomGenerator=new RandomGenerator(genomeSize,mutationNumber);

        for(int position:randomGenerator){

            int mutation=random.nextInt(7)+1;
            int currentGene=genomeList.get(position);
            System.out.println(position+" "+mutation);
            genomeList.set(position,(currentGene+mutation)%8);
        }


        AbstractGenome newGenome;
        if(genome instanceof FullPredestinationGenome){
            newGenome=new FullPredestinationGenome(genomeList);
        }else{
            newGenome=new BackAndForthGenome(genomeList);
        }

        Animal newAnimal=new Animal(position,MapDirection.values()[random.nextInt(4)],newGenome);

        return newAnimal;





    }



}
