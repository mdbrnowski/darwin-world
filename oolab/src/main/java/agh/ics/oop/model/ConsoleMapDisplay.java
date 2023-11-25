package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener {
    private int n = 0;

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        System.out.printf("Map %s\n", worldMap.getId().toString());
        System.out.printf("%d %s%n", n++, message);
        System.out.println(worldMap);
    }
}
