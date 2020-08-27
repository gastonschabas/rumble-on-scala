package com.gaston.repository

import com.danielasfregola.randomdatagenerator.RandomDataGenerator._
import com.dimafeng.testcontainers.{ForAllTestContainer, MySQLContainer}
import com.gaston.model.Hello
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.funsuite.AsyncFunSuite
import org.scalatest.matchers.should.Matchers
import play.api.db.slick.DatabaseConfigProvider
import play.api.inject.guice.GuiceApplicationBuilder

class HelloRepositoryItTest
    extends AsyncFunSuite
    with Matchers
    with ForAllTestContainer {

  override lazy val container: MySQLContainer = MySQLContainer()

  lazy val app = GuiceApplicationBuilder()
    .configure(
      "slick.dbs.default.profile" -> "slick.jdbc.MySQLProfile$",
      "slick.dbs.default.db.driver" -> container.driverClassName,
      "slick.dbs.default.db.url" -> container.jdbcUrl,
      "slick.dbs.default.db.user" -> container.username,
      "slick.dbs.default.db.password" -> container.password
    )
    .build()

  lazy val dbConfigProvider: DatabaseConfigProvider =
    app.injector.instanceOf[DatabaseConfigProvider]

  lazy val repo = new HelloRepository(dbConfigProvider)

  lazy val hellosSchemaCreated = repo.createTable

  implicit val arbitraryHello: Arbitrary[Hello] = Arbitrary {
    for {
      msg <- Gen.alphaLowerStr
      lang <- Gen.alphaLowerStr
    } yield Hello(None, msg, lang)
  }

  val helloSize = 11
  lazy val randomHellos: Seq[Hello] = random[Hello](helloSize)
  lazy val randomHelloNotLoaded: Hello = randomHellos.head
  lazy val randomHellosForInitialLoading: Seq[Hello] = randomHellos.tail

  lazy val hellosInitialDataLoaded = for {
    _ <- hellosSchemaCreated
    _ <- repo.save(randomHellosForInitialLoading)
  } yield {}

  randomHellosForInitialLoading.foreach { randomHello =>
    test(s"${randomHello.lang} language should be found") {
      for {
        _ <- hellosInitialDataLoaded
        maybeHello <- repo.find(randomHello.lang)
      } yield {
        maybeHello.isDefined should be(true)
      }
    }
  }

  test(s"${randomHelloNotLoaded.lang} language should not be found") {
    for {
      _ <- hellosInitialDataLoaded
      maybeHello <- repo.find(randomHelloNotLoaded.lang)
    } yield {
      maybeHello.isDefined should be(false)
    }
  }
}
