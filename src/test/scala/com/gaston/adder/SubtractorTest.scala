package com.gaston.adder

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class SubtractorTest extends AnyFunSuite with Matchers {

  test("1 - 1 should be 0") {
    val adder = new Subtractor
    adder.run(1, 1) should be(0)
  }

  test("2 - 1 should be 1") {
    val adder = new Subtractor
    adder.run(2, 1) should be(1)
  }

}
