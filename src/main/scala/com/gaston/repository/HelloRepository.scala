package com.gaston.repository

import com.gaston.model.Hello
import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.Future

@Singleton
@SuppressWarnings(Array("org.wartremover.warts.Any"))
class HelloRepository @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import dbConfig.profile.api._

  class Hellos(tag: Tag) extends Table[Hello](tag, "hellos") {
    def id: Rep[Long] =
      column[Long]("id", O.PrimaryKey, O.AutoInc)
    def msg: Rep[String] = column[String]("msg")
    def lang: Rep[String] = column[String]("lang")
    def * : ProvenShape[Hello] =
      (id.?, msg, lang) <> (Hello.tupled, Hello.unapply)
    val uniqueLangHelloIndex = index("idx_lang", lang, true)
  }

  val hellosQuery: TableQuery[Hellos] = TableQuery[Hellos]
  private lazy val hellosInsertQuery =
    hellosQuery returning hellosQuery.map(_.id) into ((h, id) =>
      h.copy(id = Some(id))
    )

  def save(hello: Seq[Hello]): Future[Seq[Hello]] =
    db.run(hellosInsertQuery ++= hello)

  def find(lang: String): Future[Option[Hello]] =
    db.run(hellosQuery.filter(_.lang === lang).take(1).result.headOption)
}
