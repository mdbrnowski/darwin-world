package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;

public class World {
    public static void main(String[] args) {
        var a = new Animal();
        a.move(MoveDirection.RIGHT);
        System.out.println(a);
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
