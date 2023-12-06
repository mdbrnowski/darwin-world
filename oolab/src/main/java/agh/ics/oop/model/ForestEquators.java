package agh.ics.oop.model;

import java.util.ArrayList;

//preferredFields are only along the equator (middle row of the map)
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

    @Override
    public void actualizePreferred(WorldMap map) {

        int minX=map.getCurrentBounds().bottomLeft().getX();
        int maxX=map.getCurrentBounds().topRight().getX();
        int minY=map.getCurrentBounds().bottomLeft().getY();
        int maxY=map.getCurrentBounds().topRight().getY();
        int meanY=(maxY+minY)/2;

        preferredFields = new ArrayList<>();

        for(int i=minX;i<=maxX;i++){
            if(!(map.objectAt(new Vector2d(i,meanY)) instanceof Grass))
                preferredFields.add(new Vector2d(i,meanY));
        }

        notPreferredFields = new ArrayList<>();
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                if(j!=meanY && !(map.objectAt(new Vector2d(i,j)) instanceof Grass))
                    notPreferredFields.add(new Vector2d(i, j));
            }
        }

    }
}
