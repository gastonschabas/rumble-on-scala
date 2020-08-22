ThisBuild / scalaVersion     := "2.12.11"
ThisBuild / version          := "0.0.0"
ThisBuild / organization     := "com.gaston"
ThisBuild / organizationName := "gaston-schabas"
ThisBuild / scapegoatVersion := "1.4.5"

bintrayRepository := "rumble-on-scala"
licenses += ("GPL-3.0", url(
  "https://github.com/gastonschabas/rumble-on-scala/blob/master/LICENSE"
))

lazy val root = (project in file("."))
  .settings(
    name := "rumble-on-scala",
    libraryDependencies ++= Seq(
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % "test",
      guice
    )
  )
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin)
