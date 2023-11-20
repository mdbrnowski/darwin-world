package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener {
    private int n = 0;

    @Override
    public void mapChanged(WorldMap<Animal, Vector2d> worldMap, String message) {
        System.out.printf("%d %s%n", n++, message);
        System.out.println(worldMap);
    }
}
