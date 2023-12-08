package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * preferredFields are only along the equator (middle row of the map)
 */
public class ForestEquators extends AbstractVegetation {

    public ForestEquators(int minX, int maxX, int minY, int maxY, int numberOfElements) {
        super(numberOfElements);

        int meanY = (maxY + minY) / 2;
        preferredFields = new ArrayList<>();

        for (int i = minX; i <= maxX; i++) {
            preferredFields.add(new Vector2d(i, meanY));
        }

        notPreferredFields = new ArrayList<>();
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                if (j != meanY)
                    notPreferredFields.add(new Vector2d(i, j));
            }
        }
    }

    @Override
    public List<Vector2d> getPreferred(WorldMap map) {
        return preferredFields.stream().filter(
                (elem) -> !(map.objectAt(elem) instanceof Grass)).collect(Collectors.toList());
    }

    @Override
    public List<Vector2d> getNotPreferred(WorldMap map) {
        return notPreferredFields.stream().filter(
                (elem) -> !(map.objectAt(elem) instanceof Grass)).collect(Collectors.toList());
    }
}
