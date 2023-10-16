package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class World {
    public static void main(String[] args) {
        System.out.println("Start");
        run(OptionsParser.parse(args));
        System.out.println("Stop");
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
