package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LifeGivingCorpses extends AbstractVegetation {
    public LifeGivingCorpses(int minX, int maxX, int minY, int maxY, int numberOfElements) {

        super(numberOfElements);

        notPreferredFields = new ArrayList<>();
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                notPreferredFields.add(new Vector2d(i, j));
            }
        }
    }

    /*
     * Returns fields around those on which an animal has died recently
     */
    @Override
    public List<Vector2d> getPreferred(AbstractWorldMap map) {
        Map<Vector2d, Animal> recentlyDead = map.getRecentlyDead();
        List<Vector2d> newPreferred = recentlyDead.keySet().stream()
                .flatMap(key -> Stream.of(
                        key,
                        new Vector2d((key.getX() - 1), key.getY()),
                        new Vector2d((key.getX() + 1), key.getY()),
                        new Vector2d((key.getX()), key.getY() - 1),
                        new Vector2d((key.getX()), key.getY() + 1),
                        new Vector2d((key.getX() - 1), key.getY() - 1),
                        new Vector2d((key.getX() - 1), key.getY() + 1),
                        new Vector2d((key.getX() + 1), key.getY() - 1),
                        new Vector2d((key.getX() + 1), key.getY() + 1)
                )).collect(Collectors.toCollection(ArrayList::new));

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
