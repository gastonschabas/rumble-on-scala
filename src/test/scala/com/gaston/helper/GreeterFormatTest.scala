package com.gaston.helper

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class GreeterFormatTest extends AnyFunSuite with Matchers {

  test("hello to Juan") {
    GreeterFormat.hello("Juan") should be("Hello Juan")
  }

  test("hello to Pepe") {
    GreeterFormat.hello("Pepe") should be("Hello Pepe")
  }
}
