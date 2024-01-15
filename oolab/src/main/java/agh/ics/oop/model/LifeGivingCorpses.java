package agh.ics.oop.model;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LifeGivingCorpses extends AbstractVegetation {
    public LifeGivingCorpses(int width, int height, int numberOfElements) {
        super(numberOfElements);

        for (int i = 0; i <= width; i++)
            for (int j = 0; j <= height; j++)
                notPreferredFields.add(new Vector2d(i, j));
    }

    /**
     * Returns fields around those on which an animal has died recently
     */
    @Override
    public Set<Vector2d> getPreferred(AbstractWorldMap map) {
        Set<Vector2d> recentlyDead = map.getRecentlyDead();
        return recentlyDead.stream()
                .flatMap(key -> Stream.of(
                        key,
                        map.getNextPosition(key, new Vector2d(-1, -1)),
                        map.getNextPosition(key, new Vector2d(-1, 0)),
                        map.getNextPosition(key, new Vector2d(0, -1)),
                        map.getNextPosition(key, new Vector2d(0, 1)),
                        map.getNextPosition(key, new Vector2d(1, 0)),
                        map.getNextPosition(key, new Vector2d(1, 1)),
                        map.getNextPosition(key, new Vector2d(-1, 1)),
                        map.getNextPosition(key, new Vector2d(1, -1))
                ))
                .filter((elem) -> map.getPlantAt(elem) == null)
                .collect(Collectors.toSet());
    }

    /**
     * returns not preferred fields - the ones that are not contained in the list returned by getPreferred
     * and on which there is no grass
     */
    @Override
    public Set<Vector2d> getNotPreferred(AbstractWorldMap map) {
        Set<Vector2d> currPreferredFields = getPreferred(map);
        return notPreferredFields.stream()
                .filter(elem -> !currPreferredFields.contains(elem) && map.getPlantAt(elem) == null)
                .collect(Collectors.toSet());
    }
}
