package com.gaston.adder

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class AdderTest extends AnyFunSuite with Matchers {

  test("1 + 1 should be 2") {
    val adder = new Adder
    adder.run(1, 1) should be(2)
  }

  test("2 + 2 should be 4") {
    val adder = new Adder
    adder.run(2, 2) should be(4)
  }

  test("2.0 + 2.0 should be 4.0") {
    val adder = new Adder
    adder.run(2.0, 2.0) should be(4.0)
  }

}
