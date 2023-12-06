package agh.ics.oop.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

//generates random array of non-repeating numbers
public class RandomGenerator implements  Iterable<Integer> {
    private List<Integer> unused=new ArrayList<Integer>();
    private Random random;

    private List<Integer> used=new ArrayList<Integer>();


    public RandomGenerator(int genomeSize,  int mutationCount) {

        random=new Random();
        for(int i=0;i<genomeSize;i++){
            unused.add(i);
        }

        for(int i=0;i<mutationCount;i++){
            int size=unused.size();
            int idx=random.nextInt(size);
            int newPosition=unused.remove(idx);
            used.add(newPosition);
        }


    }

    @Override
    public Iterator<Integer> iterator() {
        return new RandomIterator(used);
    }
}
