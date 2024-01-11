package model

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MapDirectionTest : FunSpec({

    test("nextTest") {
        MapDirection.NORTH.next() shouldBe MapDirection.EAST
        MapDirection.WEST.next() shouldBe MapDirection.NORTH
        MapDirection.SOUTH.next() shouldBe MapDirection.WEST
        MapDirection.EAST.next() shouldBe MapDirection.SOUTH
    }

    test("previousTest") {
        MapDirection.NORTH.previous() shouldBe MapDirection.WEST
        MapDirection.WEST.previous() shouldBe MapDirection.SOUTH
        MapDirection.SOUTH.previous() shouldBe MapDirection.EAST
        MapDirection.EAST.previous() shouldBe MapDirection.NORTH
    }
})