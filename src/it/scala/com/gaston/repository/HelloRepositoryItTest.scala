package com.gaston.repository

import com.danielasfregola.randomdatagenerator.RandomDataGenerator._
import com.dimafeng.testcontainers.{ForAllTestContainer, PostgreSQLContainer}
import com.gaston.model.Hello
import com.gaston.module.MigrationModule
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers
import org.testcontainers.utility.DockerImageName
import play.api.db.slick.DatabaseConfigProvider
import play.api.inject.guice.GuiceApplicationBuilder

class HelloRepositoryItTest
    extends AsyncFlatSpec
    with Matchers
    with ForAllTestContainer {

  override lazy val container: PostgreSQLContainer =
    PostgreSQLContainer(dockerImageNameOverride =
      DockerImageName.parse("postgres:12.0-alpine")
    )

  lazy val app = GuiceApplicationBuilder()
    .configure(
      "slick.dbs.default.profile" -> "slick.jdbc.PostgresProfile$",
      "slick.dbs.default.db.driver" -> container.driverClassName,
      "slick.dbs.default.db.url" -> container.jdbcUrl,
      "slick.dbs.default.db.user" -> container.username,
      "slick.dbs.default.db.password" -> container.password
    )
    .bindings(new MigrationModule)
    .build()

  lazy val dbConfigProvider: DatabaseConfigProvider =
    app.injector.instanceOf[DatabaseConfigProvider]

  lazy val repo = new HelloRepository(dbConfigProvider)

  implicit val arbitraryLang: Arbitrary[Hello] = Arbitrary {
    for {
      msg <- Gen.alphaStr
      lang <- Gen
        .listOfN(2, Gen.alphaLowerChar)
        .suchThat(_.size == 2)
        .suchThat(c => !List("en", "de", "es", "fr").contains(c.mkString))
        .map(_.mkString)
    } yield Hello(id = None, msg = msg, lang = lang)
  }

  val helloSize = 15
  lazy val randomHellos: Seq[Hello] =
    random[Hello](helloSize).distinctBy(_.lang)

  lazy val randomHelloNotLoaded: Hello = randomHellos.head
  lazy val randomHellosForInitialLoading: Seq[Hello] = randomHellos.tail

  lazy val hellosInitialDataLoaded = for {
    _ <- repo.save(randomHellosForInitialLoading)
  } yield {}

  behavior of s"Langs loaded in db: ${randomHellosForInitialLoading.map(_.lang).mkString(", ")}"
  randomHellosForInitialLoading.foreach { randomHello =>
    it should s"found language ${randomHello.lang}" in {
      for {
        _ <- hellosInitialDataLoaded
        maybeHello <- repo.find(randomHello.lang)
      } yield {
        maybeHello.isDefined should be(true)
      }
    }
  }

  behavior of s"Lang not loaded in db: ${randomHelloNotLoaded.lang}"
  it should s"not found language ${randomHelloNotLoaded.lang} " in {
    for {
      _ <- hellosInitialDataLoaded
      maybeHello <- repo.find(randomHelloNotLoaded.lang)
    } yield {
      maybeHello.isDefined should be(false)
    }
  }
}
