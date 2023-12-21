package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LifeGivingCorpses extends AbstractVegetation {
    public LifeGivingCorpses(int maxX, int maxY, int numberOfElements) {

        super(numberOfElements);

        notPreferredFields = new ArrayList<>();
        for (int i = 0; i <= maxX; i++) {
            for (int j = 0; j <= maxY; j++) {
                notPreferredFields.add(new Vector2d(i, j));
            }
        }
    }

    /*
     * Returns fields around those on which an animal has died recently
     */
    @Override
    public List<Vector2d> getPreferred(AbstractWorldMap map) {
        List<Vector2d> recentlyDead = map.getRecentlyDead();
        Set<Vector2d> newPreferred = recentlyDead.stream()
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
                .collect(Collectors.toSet());

        return newPreferred.stream().filter(
                (elem) -> !(map.objectAt(elem) instanceof Grass)).collect(Collectors.toList());
    }

    /*
     * returns not preferred fields - the ones that are not contained in the list returned by getPreferred
     * and on which there is no grass
     */
    @Override
    public List<Vector2d> getNotPreferred(AbstractWorldMap map) {
        List<Vector2d> currPreferredFields = getPreferred(map);

        return notPreferredFields.stream()
                .filter(vector2d -> !currPreferredFields.contains(vector2d))
                .filter((elem) -> !(map.objectAt(elem) instanceof Grass)).collect(Collectors.toList());
    }


}
