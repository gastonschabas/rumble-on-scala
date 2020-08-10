ThisBuild / scalaVersion     := "2.12.11"
ThisBuild / version          := "0.0.0"
ThisBuild / organization     := "com.gaston"
ThisBuild / organizationName := "gaston-schabas"
ThisBuild / scapegoatVersion := "1.4.5"

lazy val root = (project in file("."))
  .settings(
    name := "rumble-on-scala",
    libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "3.2.0" % Test)
  )
