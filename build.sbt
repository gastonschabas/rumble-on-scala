import com.typesafe.sbt.SbtNativePackager.autoImport.NativePackagerHelper._
import com.typesafe.sbt.packager.docker.{
  DockerChmodType,
  DockerPermissionStrategy
}

ThisBuild / scalaVersion     := "2.12.11"
ThisBuild / organization     := "com.gaston"
ThisBuild / organizationName := "gaston-schabas"
ThisBuild / scapegoatVersion := "1.4.6"

licenses += ("GPL-3.0", url(
  "https://github.com/gastonschabas/rumble-on-scala/blob/master/LICENSE"
))

lazy val testContainerVersion = "0.38.7"
lazy val playPort = 9000

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    name := "rumble-on-scala",
    resolvers += Resolver.jcenterRepo,
    libraryDependencies ++= Seq(
      "org.scalatestplus.play" %% "scalatestplus-play"              % "5.1.0"              % "it, test",
      "com.typesafe.play"      %% "play-slick"                      % "5.0.0",
      "com.dimafeng"           %% "testcontainers-scala-scalatest"  % testContainerVersion % "it",
      "com.dimafeng"           %% "testcontainers-scala-postgresql" % testContainerVersion % "it",
      "org.postgresql"          % "postgresql"                      % "42.2.18",
      "com.danielasfregola"    %% "random-data-generator"           % "2.9"                % "it, test",
      "io.github.nafg"         %% "slick-migration-api-flyway"      % "0.7.0",
      "org.scalamock"          %% "scalamock"                       % "5.0.0"              % Test,
      guice
    ),
    mappings in Universal ++= directory(
      baseDirectory.value / "src" / "main" / "resources"
    ),
    packageName in Docker    := packageName.value,
    version in Docker        := version.value,
    dockerRepository         := Some("gastonschabas"),
    dockerBaseImage          := "adoptopenjdk:11-jre-openj9",
    dockerExposedPorts       := Seq(playPort),
    dockerLabels             := Map("maintainer" -> "gastonschabas@gmail.com"),
    dockerChmodType          := DockerChmodType.UserGroupWriteExecute,
    dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
  )
  .enablePlugins(PlayScala)
  .enablePlugins(DockerPlugin)
  .enablePlugins(JavaAppPackaging)
  .disablePlugins(PlayLayoutPlugin)
