package agh.ics.oop.model;

import java.util.List;

public class LifeGivingCorpses extends AbstractVegetation{
    public LifeGivingCorpses(int numberOfElements) {
        super(numberOfElements);
    }

    @Override
    public List<Vector2d> getPreferred(WorldMap map) {
        // todo
        return null;
    }

    @Override
    public List<Vector2d> getNotPreferred(WorldMap map) {
        // todo
        return null;
    }
}
