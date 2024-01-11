package model

fun <V> Map<Vector2d, V>.randomPosition(): Vector2d? = keys.randomOrNull()

fun <V> Map<Vector2d, V>.randomFreePosition(mapSize: Vector2d): Vector2d? {
    val free: MutableSet<Vector2d> = HashSet()
    for (i in 0..<mapSize.x)
        for (j in 0..<mapSize.y)
            free.add(Vector2d(i, j))
    return free.subtract(keys).randomOrNull()
}