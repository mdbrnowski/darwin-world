package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {
    public static void main(String[] args) {
        GrassField map = new GrassField(10);
        try {
            List<MoveDirection> directions = OptionsParser.parse(args);
            List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
            Simulation simulation = new Simulation(map, positions, directions);
            simulation.run();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    public static void run(MoveDirection[] args) {
        for (MoveDirection arg : args) {
            String text = switch (arg) {
                case FORWARD -> "Zwierzak idzie do przodu";
                case BACKWARD -> "Zwierzak idzie do tyłu";
                case RIGHT -> "Zwierzak skręca w prawo";
                case LEFT -> "Zwierzak skręca w lewo";
            };
            System.out.println(text);
        }
    }
}
