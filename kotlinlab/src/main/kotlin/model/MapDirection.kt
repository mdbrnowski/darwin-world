package model

enum class MapDirection {
    NORTH, WEST, SOUTH, EAST;

    fun next(): MapDirection = entries[(ordinal - 1 + entries.size) % entries.size]

    fun previous(): MapDirection = entries[(ordinal + 1) % entries.size]

    override fun toString() = when (this) {
        NORTH -> "Północ"
        WEST -> "Zachód"
        SOUTH -> "Południe"
        EAST -> "Wschód"
    }
}
