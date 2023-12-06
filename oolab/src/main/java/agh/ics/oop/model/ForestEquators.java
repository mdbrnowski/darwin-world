package agh.ics.oop.model;

import java.util.ArrayList;

public class ForestEquators extends AbstractVegetation{

    public ForestEquators(int minX,int maxX,int minY,int maxY,int numberOfElements){
        super(numberOfElements);


        int meanY=(maxY+minY)/2;
        preferredFields = new ArrayList<>();

        for(int i=minX;i<=maxX;i++){
            preferredFields.add(new Vector2d(i,meanY));
        }

        notPreferredFields = new ArrayList<>();
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                if(j!=meanY)
                    notPreferredFields.add(new Vector2d(i, j));
            }
        }
    }
}
