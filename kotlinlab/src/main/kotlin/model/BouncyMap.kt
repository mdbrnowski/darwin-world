package model

class BouncyMap(private val width: Int, private val height: Int) : IWorldMap {
    private var animals: MutableMap<Vector2d, Animal> = HashMap()

    override fun canMoveTo(position: Vector2d): Boolean =
        position.precedes(Vector2d(width - 1, height - 1)) && position.follows(Vector2d(0, 0))

    override fun place(animal: Animal) {
        if (isOccupied(animal.position)) {
            val newPosition: Vector2d? = animals.randomFreePosition(Vector2d(width, height)) ?: animals.randomPosition()
            if (newPosition != null)
                animal.position = newPosition
            else
                throw RuntimeException("Something went wrong here.")
        }
        animals[animal.position] = animal
    }

    override fun move(animal: Animal, direction: MoveDirection) {
        animals.remove(animal.position)
        animal.move(direction, this)
        place(animal)
    }

    override fun isOccupied(position: Vector2d): Boolean = animals[position] != null

    override fun objectAt(position: Vector2d): Animal? = animals[position]

    override fun getElements(): Collection<Animal> = animals.values;
}