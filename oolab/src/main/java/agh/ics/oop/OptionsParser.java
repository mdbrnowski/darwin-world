package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.Set;

public class OptionsParser {

    public static MoveDirection[] parse(String[] args) {
        int numberOfDirections = 0;
        for (String arg : args) {
            if (Set.of("f", "b", "r", "l").contains(arg))
                numberOfDirections++;
        }
        var directions = new MoveDirection[numberOfDirections];
        int i = 0;
        for (String arg : args) {
            switch (arg) {
                case "f" -> directions[i] = MoveDirection.FORWARD;
                case "b" -> directions[i] = MoveDirection.BACKWARD;
                case "r" -> directions[i] = MoveDirection.RIGHT;
                case "l" -> directions[i] = MoveDirection.LEFT;
                default -> i--;
            };
            i++;
        }
        return directions;
    }
}
