package model

enum class MapDirection {
    NORTH, WEST, SOUTH, EAST;

    fun next(): MapDirection = entries[(ordinal - 1 + entries.size) % entries.size]

    fun previous(): MapDirection = entries[(ordinal + 1) % entries.size]

    fun toUnitVector(): Vector2d = when (this) {
        NORTH -> Vector2d(0, 1)
        WEST -> Vector2d(-1, 0)
        SOUTH -> Vector2d(0, -1)
        EAST -> Vector2d(1, 0)
    }

    override fun toString() = when (this) {
        NORTH -> "Północ"
        WEST -> "Zachód"
        SOUTH -> "Południe"
        EAST -> "Wschód"
    }
}
