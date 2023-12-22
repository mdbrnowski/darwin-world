package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * preferredFields are only along the equator (middle row of the map)
 */
public class ForestEquators extends AbstractVegetation {

    public ForestEquators(int width, int height, int numberOfElements) {
        super(numberOfElements);

        int meanY = height / 2;
        preferredFields = new ArrayList<>();

        for (int i = 0; i <= width; i++) {
            preferredFields.add(new Vector2d(i, meanY));
        }

        notPreferredFields = new ArrayList<>();
        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height; j++) {
                if (j != meanY)
                    notPreferredFields.add(new Vector2d(i, j));
            }
        }
    }

    @Override
    public List<Vector2d> getPreferred(AbstractWorldMap map) {
        return preferredFields.stream().filter(
                (elem) -> !(map.objectAt(elem) instanceof Grass)).collect(Collectors.toList());
    }

    @Override
    public List<Vector2d> getNotPreferred(AbstractWorldMap map) {
        return notPreferredFields.stream().filter(
                (elem) -> !(map.objectAt(elem) instanceof Grass)).collect(Collectors.toList());
    }
}
