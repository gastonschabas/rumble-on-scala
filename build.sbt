ThisBuild / scalaVersion     := "2.12.11"
ThisBuild / version          := "0.0.0"
ThisBuild / organization     := "com.gaston"
ThisBuild / organizationName := "gaston-schabas"
ThisBuild / scapegoatVersion := "1.4.5"

bintrayRepository := "rumble-on-scala"
licenses += ("GPL-3.0", url(
  "https://github.com/gastonschabas/rumble-on-scala/blob/master/LICENSE"
))

lazy val testContainerVersion = "0.38.3"

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    name := "rumble-on-scala",
    libraryDependencies ++= Seq(
      "org.scalatestplus.play" %% "scalatestplus-play"              % "5.1.0"              % "it,test",
      "com.typesafe.play"      %% "play-slick"                      % "4.0.0",
      "com.dimafeng"           %% "testcontainers-scala-scalatest"  % testContainerVersion % "it",
      "com.dimafeng"           %% "testcontainers-scala-postgresql" % testContainerVersion % "it",
      "org.postgresql"          % "postgresql"                      % "42.2.5",
      "com.danielasfregola"    %% "random-data-generator"           % "2.8"                % "it",
      guice
    )
  )
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin)
