package model

import kotlin.math.max
import kotlin.math.min

data class Vector2d(val x: Int, val y: Int) {
    operator fun plus(other: Vector2d): Vector2d = Vector2d(this.x + other.x, this.y + other.y)

    operator fun minus(other: Vector2d): Vector2d = Vector2d(this.x - other.x, this.y - other.y)

    operator fun unaryMinus(): Vector2d = Vector2d(-x, -y)

    fun precedes(other: Vector2d): Boolean = this.x <= other.x && this.y <= other.y

    fun follows(other: Vector2d): Boolean = this.x >= other.x && this.y >= other.y

    fun upperRight(other: Vector2d): Vector2d = Vector2d(max(this.x, other.x), max(this.y, other.y))

    fun lowerLeft(other: Vector2d): Vector2d = Vector2d(min(this.x, other.x), min(this.y, other.y))

    override fun toString() = "($x,$y)"
}

fun MapDirection.toUnitVector(): Vector2d = when (this) {
    MapDirection.NORTH -> Vector2d(0, 1)
    MapDirection.WEST -> Vector2d(-1, 0)
    MapDirection.SOUTH -> Vector2d(0, -1)
    MapDirection.EAST -> Vector2d(1, 0)
}