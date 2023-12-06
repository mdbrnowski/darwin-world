package agh.ics.oop.model;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;
    private int energy;
    private int childrenNum;
    private int age;
    private AbstractGenome genome;



    public Animal(Vector2d position) {
        this.orientation = MapDirection.NORTH;
        this.position = position;
        this.childrenNum=0;
        this.age=0;
    }

    public Animal() {
        this(new Vector2d(2, 2));
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    @Override
    public String toString() {
        return switch (orientation) {
            case NORTH -> "↑";
            case WEST -> "←";
            case SOUTH -> "↓";
            case EAST -> "→";
        };
    }

    public void move(MoveDirection direction, MoveValidator validator) {
        switch (direction) {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> {
                Vector2d new_position = position.add(orientation.toUnitVector());
                if (validator.canMoveTo(new_position))
                    position = new_position;
            }
            case BACKWARD -> {
                Vector2d new_position = position.add(orientation.toUnitVector().opposite());
                if (validator.canMoveTo(new_position))
                    position = new_position;
            }
        }
    }

    public void incrementEnergy(int value){
        energy+=value;
    }

    public int getEnergy() {
        return energy;
    }

    public AbstractGenome getGenome() {
        return genome;
    }

//    public void breed(Animal animal){
//        double energyPart= (double) this.energy /(animal.getEnergy()+this.energy);
//        Animal newAnimal=new Animal(this.getPosition(),)
//    }


}
