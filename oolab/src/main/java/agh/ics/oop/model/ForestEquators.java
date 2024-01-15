package agh.ics.oop.model;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * preferredFields are only along the equator (middle row of the map)
 */
public class ForestEquators extends AbstractVegetation {

    public ForestEquators(int width, int height, int numberOfElements) {
        super(numberOfElements);

        int meanY = height / 2;
        for (int i = 0; i <= width; i++)
            preferredFields.add(new Vector2d(i, meanY));

        for (int i = 0; i <= width; i++)
            for (int j = 0; j <= height; j++)
                if (j != meanY)
                    notPreferredFields.add(new Vector2d(i, j));
    }

    @Override
    public Set<Vector2d> getPreferred(AbstractWorldMap map) {
        return preferredFields.stream()
                .filter((elem) -> map.getPlantAt(elem) == null)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Vector2d> getNotPreferred(AbstractWorldMap map) {
        return notPreferredFields.stream()
                .filter((elem) -> map.getPlantAt(elem) == null)
                .collect(Collectors.toSet());
    }
}
