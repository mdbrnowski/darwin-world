package model

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class Vector2dTest : FunSpec({

    test("equals") {
        val v1 = Vector2d(1, 2)
        val v2 = Vector2d(2, 2)
        val v3 = Vector2d(1, 2)
        v1 shouldBe v3
        v3 shouldBe v1
        v1 shouldNotBe v2
        v2 shouldNotBe v1
    }

    test("toString") {
        Vector2d(1, 2).toString() shouldBe "(1,2)"
    }

    test("precedes") {
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

    test("follows") {
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

    test("upperRight") {
        val v1 = Vector2d(0, 0)
        val v2 = Vector2d(2, 0)
        val v3 = Vector2d(2, 2)
        val v4 = Vector2d(0, 2)
        v1.upperRight(v2) shouldBe v2
        v1.upperRight(v4) shouldBe v4
        v2.upperRight(v4) shouldBe v3
    }

    test("lowerLeft") {
        val v1 = Vector2d(0, 0)
        val v2 = Vector2d(2, 0)
        val v3 = Vector2d(2, 2)
        val v4 = Vector2d(0, 2)
        v1.lowerLeft(v2) shouldBe v1
        v3.lowerLeft(v4) shouldBe v4
        v2.lowerLeft(v4) shouldBe v1
    }

    test("plus") {
        Vector2d(1, 2) + Vector2d(-2, -1) shouldBe Vector2d(-1, 1)
    }

    test("minus") {
        Vector2d(1, 2) - Vector2d(-2, -1) shouldBe Vector2d(3, 3)
    }

    test("opposite") {
        val v1 = Vector2d(1, 2)
        val v2 = Vector2d(-1, -2)
        -v1 shouldBe v2
        -(-v1) shouldBe v1
    }
})