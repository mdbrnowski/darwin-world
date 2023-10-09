package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("System wystartował");
        run(args);
        System.out.println("System zakończył działanie");
    }
    public static void run(String[] args) {
        System.out.println("Zwierzak idzie do przodu");
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
            if (i < args.length - 1)
                System.out.print(", ");
        }
        System.out.println();
    }
}
