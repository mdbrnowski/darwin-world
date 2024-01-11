package model

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class Vector2dTest : FunSpec({

    test("equalsTest") {
        val v1 = Vector2d(1, 2)
        val v2 = Vector2d(2, 2)
        val v3 = Vector2d(1, 2)
        v1 shouldBe v3
        v3 shouldBe v1
        v1 shouldNotBe v2
        v2 shouldNotBe v1
    }

    test("toStringTest") {
        val v = Vector2d(1, 2)
        v.toString() shouldBe "(1,2)"
    }

    test("precedesTest") {
        val v1 = Vector2d(0, 0)
        val v2 = Vector2d(2, 0)
        val v3 = Vector2d(2, 2)
        val v4 = Vector2d(0, 2)
        v1.precedes(v1) shouldBe true
        v1.precedes(v2) shouldBe true
        v1.precedes(v3) shouldBe true
        v1.precedes(v4) shouldBe true
        v2.precedes(v2) shouldBe true
        v2.precedes(v3) shouldBe true
        v4.precedes(v3) shouldBe true
        v4.precedes(v4) shouldBe true
    }

    test("followsTest") {
        val v1 = Vector2d(0, 0)
        val v2 = Vector2d(2, 0)
        val v3 = Vector2d(2, 2)
        val v4 = Vector2d(0, 2)
        v1.follows(v1) shouldBe true
        v2.follows(v1) shouldBe true
        v2.follows(v2) shouldBe true
        v3.follows(v1) shouldBe true
        v3.follows(v2) shouldBe true
        v3.follows(v4) shouldBe true
        v4.follows(v1) shouldBe true
        v4.follows(v4) shouldBe true
    }

    test("upperRightTest") {
        val v1 = Vector2d(0, 0)
        val v2 = Vector2d(2, 0)
        val v3 = Vector2d(2, 2)
        val v4 = Vector2d(0, 2)
        v1.upperRight(v2) shouldBe v2
        v1.upperRight(v4) shouldBe v4
        v2.upperRight(v4) shouldBe v3
    }

    test("lowerLeftTest") {
        val v1 = Vector2d(0, 0)
        val v2 = Vector2d(2, 0)
        val v3 = Vector2d(2, 2)
        val v4 = Vector2d(0, 2)
        v1.lowerLeft(v2) shouldBe v1
        v3.lowerLeft(v4) shouldBe v4
        v2.lowerLeft(v4) shouldBe v1
    }

    test("addTest") {
        val v1 = Vector2d(1, 2)
        val v2 = Vector2d(-2, -1)
        val v3 = Vector2d(-1, 1)
        v1 + v2 shouldBe v3
    }

    test("subtractTest") {
        val v1 = Vector2d(1, 2)
        val v2 = Vector2d(-2, -1)
        val v3 = Vector2d(3, 3)
        v1 - v2 shouldBe v3
    }

    test("oppositeTest") {
        val v1 = Vector2d(1, 2)
        val v2 = Vector2d(-1, -2)
        -v1 shouldBe v2
        -(-v1) shouldBe v1
    }
})