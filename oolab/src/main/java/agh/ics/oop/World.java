package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.PositionAlreadyOccupiedException;
import javafx.application.Application;

import java.rmi.AlreadyBoundException;
import java.util.List;


public class World {
    public static void main(String[] args) {
//        Application.launch(SimulationApp.class, args);
//        System.out.println("The system has completed the operation.");

        FullPredestinationGenome genome1=new FullPredestinationGenome(List.of(1,2,3,4,5,0,7));
        FullPredestinationGenome genome2=new FullPredestinationGenome(List.of(0,5,2,3,2,7,1));

        Animal animal1=new Animal(new Vector2d(1,2), MapDirection.NORTH,genome1);
        Animal animal2=new Animal(new Vector2d(4,5), MapDirection.NORTHEAST,genome2);



        animal1.setEnergy(50);
        animal2.setEnergy(50);

        animal2.breed(animal1,2,5);

        ForestEquators vegetator=new ForestEquators(0,4,0,5,8);

        EarthGlobe globe=new EarthGlobe(4,5,vegetator);
        globe.addObserver(new ConsoleMapDisplay());
        System.out.println(globe);
        try{
            globe.place(animal1);
            globe.place(animal2);
        }catch (PositionAlreadyOccupiedException e) {
            throw new RuntimeException(e);
        }
        globe.move(animal2);
        System.out.println(globe);
        System.out.println(globe.getAnimals());



    }
}
