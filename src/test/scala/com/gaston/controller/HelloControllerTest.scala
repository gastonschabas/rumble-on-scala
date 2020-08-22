package com.gaston.controller

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import play.api.mvc.Results
import play.api.test.{FakeRequest, Helpers}
import play.api.test.Helpers._

class HelloControllerTest extends AnyFunSuite with Matchers with Results {

  test("/v0/hello should return HTTP status code 200") {
    val controller = new HelloController(Helpers.stubControllerComponents())
    val result = controller.index.apply(FakeRequest())
    status(result) should be(OK)
  }

  test("/v0/hello should return hello world") {
    val controller = new HelloController(Helpers.stubControllerComponents())
    val result = controller.index.apply(FakeRequest())
    val text = contentAsString(result)
    text should be("hello world")
  }

}
