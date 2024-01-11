package model

class Animal(var position: Vector2d = Vector2d(0, 0)) {
    var orientation: MapDirection = MapDirection.NORTH
        private set

    fun isAt(position: Vector2d): Boolean = this.position == position

    fun move(direction: MoveDirection, validator: IWorldMap) {
        when (direction) {
            MoveDirection.RIGHT -> orientation = orientation.next()
            MoveDirection.LEFT -> orientation = orientation.previous()
            MoveDirection.FORWARD -> {
                val newPosition = position + orientation.toUnitVector()
                if (validator.canMoveTo(newPosition))
                    position = newPosition
            }
            MoveDirection.BACKWARD -> {
                val newPosition = position - orientation.toUnitVector()
                if (validator.canMoveTo(newPosition))
                    position = newPosition
            }
        }
    }

    override fun toString(): String = when (orientation) {
        MapDirection.NORTH -> "↑"
        MapDirection.WEST -> "←"
        MapDirection.SOUTH -> "↓"
        MapDirection.EAST -> "→"
    }
}