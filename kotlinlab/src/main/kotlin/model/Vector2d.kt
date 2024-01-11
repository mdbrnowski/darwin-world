package model

import kotlin.math.max
import kotlin.math.min

data class Vector2d(val x: Int, val y: Int) {
    operator fun plus(other: Vector2d): Vector2d {
        return Vector2d(this.x + other.x, this.y + other.y)
    }

    operator fun minus(other: Vector2d): Vector2d {
        return Vector2d(this.x - other.x, this.y - other.y)
    }

    operator fun unaryMinus() = Vector2d(-x, -y)

    fun precedes(other: Vector2d): Boolean {
        return this.x <= other.x && this.y <= other.y
    }

    fun follows(other: Vector2d): Boolean {
        return this.x >= other.x && this.y >= other.y
    }

    fun upperRight(other: Vector2d): Vector2d {
        return Vector2d(max(this.x, other.x), max(this.y, other.y))
    }

    fun lowerLeft(other: Vector2d): Vector2d {
        return Vector2d(min(this.x, other.x), min(this.y, other.y))
    }

    override fun toString() = "($x,$y)"
}
