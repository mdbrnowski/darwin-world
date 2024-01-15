package agh.ics.oop.model.util;

import agh.ics.oop.model.Animal;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DescendantCounter {
    public static synchronized void increaseDescendantsNum(Animal animal){
        HashMap<Animal,Boolean> visited=new HashMap<>();
        LinkedList<Animal> queue=new LinkedList<>();

        queue.addLast(animal);

        while(!queue.isEmpty()){
            Animal currAnimal=queue.getFirst();
            queue.removeFirst();
            for(Animal parent: animal.getParents()){
                if(visited.get(parent)==null){
                    visited.put(parent,true);
                    parent.increaseDescendantsNum(1);
                    queue.addLast(parent);
                }
            }

        }
    }
}
