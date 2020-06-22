ThisBuild / scalaVersion     := "2.13.2"
ThisBuild / version          := "0.0.0"
ThisBuild / organization     := "com.gaston"
ThisBuild / organizationName := "gaston-schabas"

lazy val root = (project in file("."))
  .settings(
    name := "rumble-on-scala",
    libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "3.2.0" % Test)
  )
