package com.gaston.greeter

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class GreeterTest extends AnyFunSuite with Matchers {

  test("returns hello $name") {
    val greeter = new Greeter
    greeter.sayHelloTo("gaston") should be("hello gaston")
  }

  test("returns hello $name") {
    val greeter = new Greeter
    greeter.sayHiTo("gaston") should be("hi gaston")
  }

}
