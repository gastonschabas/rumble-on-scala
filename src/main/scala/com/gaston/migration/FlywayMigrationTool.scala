package com.gaston.migration

import com.gaston.repository.HelloRepository
import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.migration.api.TableMigration.Action
import slick.migration.api.flyway.{
  MigrationInfo,
  SlickFlyway,
  VersionedMigration
}
import slick.migration.api.{Migration, PostgresDialect, TableMigration}

@Singleton
class FlywayMigrationTool @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider,
  val helloRepository: HelloRepository
) extends HasDatabaseConfigProvider[JdbcProfile] {

  implicit val dialect = new PostgresDialect
  implicit val infoProvider: MigrationInfo.Provider[Migration] =
    MigrationInfo.Provider.strict

  val helloTableCreation
    : TableMigration[helloRepository.Hellos, Action.Reversible] =
    TableMigration(helloRepository.hellosQuery).create
      .addColumns(_.id, _.msg, _.lang)
      .addIndexes(_.uniqueLangHelloIndex)

  val helloTableMigration = VersionedMigration("1", helloTableCreation)

  val flyway =
    SlickFlyway(db)(Seq(helloTableMigration))
      .load()

  def migrate(): Unit = {
    val _ = flyway.migrate()
    ()
  }

  migrate()

}
