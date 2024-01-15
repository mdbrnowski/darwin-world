package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener {
    private int n = 0;

    @Override
    public synchronized void mapChanged(AbstractWorldMap map, String message) {
        System.out.printf("Map %s\n", map.getId().toString());
        System.out.printf("%d %s%n", n++, message);
        System.out.println(map);
    }
}
