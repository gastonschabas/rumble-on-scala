package com.gaston.module

import com.google.inject.AbstractModule
import com.gaston.migration.FlywayMigrationTool

class MigrationModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[FlywayMigrationTool]).asEagerSingleton()
  }

}
