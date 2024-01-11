package model

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class BouncyMapTest : FunSpec({

    test("trivial animal placement") {
        val map = BouncyMap(3, 1)
        val a = Animal(Vector2d(0, 0))
        val b = Animal(Vector2d(1, 0))
        val c = Animal(Vector2d(2, 0))
        map.place(a)
        map.place(b)
        map.place(c)
        map.getElements().toSet() shouldBe setOf(a, b, c)
    }

    test("animal placement with free positions") {
        val map = BouncyMap(3, 1)
        val a = Animal(Vector2d(0, 0))
        val b = Animal(Vector2d(0, 0))
        val c = Animal(Vector2d(0, 0))
        map.place(a)
        map.place(b)
        map.place(c)
        map.getElements().toSet() shouldBe setOf(a, b, c)
    }

    test("animal placement without free positions") {
        val map = BouncyMap(2, 1)
        val a = Animal(Vector2d(0, 0))
        val b = Animal(Vector2d(0, 0))
        val c = Animal(Vector2d(0, 0))
        map.place(a)
        map.place(b)
        map.place(c)
        c shouldBeIn map.getElements().toSet()
        map.getElements() shouldHaveSize 2
    }

    test("animal move to the border") {
        val map = BouncyMap(3, 3)
        val a = Animal()
        map.move(a, MoveDirection.FORWARD)
        map.move(a, MoveDirection.FORWARD)
        map.move(a, MoveDirection.FORWARD)
        map.move(a, MoveDirection.LEFT)
        a.isAt(Vector2d(0, 2)) shouldBe true
        a shouldBeIn map.getElements()
        map.getElements() shouldHaveSize 1
        a.orientation shouldBe MapDirection.WEST
    }
})