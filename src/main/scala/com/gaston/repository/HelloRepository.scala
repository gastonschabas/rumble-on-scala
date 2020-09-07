package com.gaston.repository

import com.gaston.model.Hello
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape
import slick.sql.SqlProfile.ColumnOption.SqlType

import scala.concurrent.Future

class HelloRepository @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import dbConfig.profile.api._

  private class Hellos(tag: Tag) extends Table[Hello](tag, "hellos") {
    def id: Rep[Long] =
      column[Long]("id", SqlType("SERIAL"), O.PrimaryKey, O.AutoInc)
    def msg: Rep[String] = column[String]("msg")
    def lang: Rep[String] = column[String]("lang")
    def * : ProvenShape[Hello] =
      (id.?, msg, lang) <> (Hello.tupled, Hello.unapply)
  }

  private val hellosQuery = TableQuery[Hellos]
  private val hellosInsertQuery = hellosQuery returning hellosQuery.map(
    _.id
  ) into ((h, id) => h.copy(id = Some(id)))

  def createTable: Future[Unit] = db.run(hellosQuery.schema.createIfNotExists)

  def save(hello: Seq[Hello]): Future[Seq[Hello]] =
    db.run(hellosInsertQuery ++= hello)

  def find(lang: String): Future[Option[Hello]] =
    db.run(hellosQuery.filter(_.lang === lang).take(1).result.headOption)
}