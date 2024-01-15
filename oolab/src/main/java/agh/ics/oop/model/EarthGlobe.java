package agh.ics.oop.model;

public class EarthGlobe extends AbstractWorldMap {

    public EarthGlobe(int width, int height) {
        super(width, height);
    }

    @Override
    public void move(Animal animal) {
        Vector2d oldPosition = animal.getPosition();

        MapDirection newOrientation = animal.getOrientation().add(animal.getGenome().iterate(animal.getAge()));
        animal.setOrientation(newOrientation);

        Vector2d newCandidate = animal.getPosition().add(animal.getOrientation().toUnitVector());

        Vector2d newPosition;
        if (newCandidate.y() > height || newCandidate.y() < 0) {
            animal.setOrientation(animal.getOrientation().reverse());
            newPosition = new Vector2d(Math.floorMod(newCandidate.x(), width + 1), oldPosition.y());
        } else {
            newPosition = new Vector2d(Math.floorMod(newCandidate.x(), width + 1), newCandidate.y());
        }
        animal.move(newPosition);

        animals.remove(oldPosition, animal);
        animals.put(animal.getPosition(), animal);
    }

    @Override
    public Vector2d getNextPosition(Vector2d position, Vector2d move) {
        Vector2d newCandidate = position.add(move);
        if (newCandidate.y() > height || newCandidate.y() < 0)
            return new Vector2d(Math.floorMod(newCandidate.x(), width + 1), position.y());
        else
            return new Vector2d(Math.floorMod(newCandidate.x(), width + 1), newCandidate.y());
    }
}
