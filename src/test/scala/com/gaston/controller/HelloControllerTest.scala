package com.gaston.controller

import com.danielasfregola.randomdatagenerator.RandomDataGenerator
import com.gaston.model.Hello
import com.gaston.repository.HelloRepository
import org.scalacheck.{Arbitrary, Gen}
import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import play.api.i18n.Lang
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class HelloControllerTest
    extends AnyFunSuite
    with Matchers
    with Results
    with MockFactory
    with RandomDataGenerator {

  trait RandomHello {
    implicit val arbitraryHello: Arbitrary[Hello] = Arbitrary {
      for {
        lang <- Gen.oneOf("es", "en", "fr", "de")
      } yield Hello(random[Option[Long]], random[String], lang)
    }
    val randomHello = random[Hello]
  }

  trait HelloRepositoryFoundSomeHelloStubFixture extends RandomHello {
    val randomHelloOpt = Option(randomHello)
    val helloRepository = stub[HelloRepository]
    (helloRepository.find _)
      .when(*)
      .returns(Future(randomHelloOpt))
  }

  trait HelloRepositoryNotFoundHelloStubFixture extends RandomHello {
    val helloRepository = stub[HelloRepository]
    (helloRepository.find _)
      .when(*)
      .returns(Future(None))
  }

  test("/v0/hello should return HTTP status code 200") {
    new HelloRepositoryFoundSomeHelloStubFixture {
      val controller =
        new HelloController(
          Helpers.stubControllerComponents(),
          helloRepository,
          Helpers.stubLangs()
        )
      val result = controller.index.apply(FakeRequest())
      status(result) should be(OK)
    }
  }

  test(
    "/v0/hello should return hello message when hello was found in the hello repository"
  ) {
    new HelloRepositoryFoundSomeHelloStubFixture {
      val controller =
        new HelloController(
          Helpers.stubControllerComponents(),
          helloRepository,
          Helpers.stubLangs()
        )
      val result = controller.index.apply(FakeRequest())
      val text = contentAsString(result)
      text should be(randomHello.msg)
    }
  }

  test(
    "/v0/hello should return hello message not found when hello was not found in the hello repository"
  ) {
    new HelloRepositoryNotFoundHelloStubFixture {
      val controller =
        new HelloController(
          Helpers.stubControllerComponents(),
          helloRepository,
          Helpers.stubLangs(Seq(Lang(randomHello.lang)))
        )
      val result = controller.index.apply(
        FakeRequest().withHeaders("Accept-Language" -> randomHello.lang)
      )
      val text = contentAsString(result)
      text should be(s"hello not found for lang ${randomHello.lang}")
    }
  }

}
