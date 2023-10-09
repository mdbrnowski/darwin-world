package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("Start");
        run(args);
        System.out.println("Stop");
    }
    public static void run(String[] args) {
        for (String arg : args) {
            String text = switch (arg) {
                case "f" -> "Zwierzak idzie do przodu";
                case "b" -> "Zwierzak idzie do tyłu";
                case "r" -> "Zwierzak skręca w prawo";
                case "l" -> "Zwierzak skręca w lewo";
                default -> "";
            };
            System.out.println(text);
        }

    }
}
