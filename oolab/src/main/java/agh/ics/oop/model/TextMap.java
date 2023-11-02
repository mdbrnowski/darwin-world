package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.swap;

public class TextMap implements WorldMap<String, Integer> {
    private final List<String> strings;

    public TextMap() {
        this.strings = new ArrayList<>();
    }

    public boolean canMoveTo(Integer position) {
        return 0 <= position && position < strings.size();
    }

    public boolean place(String animal) {
        strings.add(animal);
        return true;
    }

    public void move(String animal, MoveDirection direction) {
        int position = strings.indexOf(animal);
        if (position > -1) {
            int new_position = switch (direction) {
                case BACKWARD -> position - 1;
                case FORWARD -> position + 1;
                default -> position;
            };
            if (new_position == -1) new_position = 0;
            if (new_position == strings.size()) new_position = strings.size() - 1;
            swap(strings, position, new_position);
        }
    }

    public boolean isOccupied(Integer position) {
        return canMoveTo(position);
    }

    public String objectAt(Integer position) {
        return strings.get(position);
    }
}
