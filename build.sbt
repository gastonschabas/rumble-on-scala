import com.typesafe.sbt.SbtNativePackager.autoImport.NativePackagerHelper._
import com.typesafe.sbt.packager.docker.{
  DockerChmodType,
  DockerPermissionStrategy
}

ThisBuild / scalaVersion     := "2.13.4"
ThisBuild / organization     := "com.gaston"
ThisBuild / organizationName := "gaston-schabas"

licenses += ("GPL-3.0", url(
  "https://github.com/gastonschabas/rumble-on-scala/blob/master/LICENSE"
))

lazy val testContainerVersion = "0.39.3"
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
      "org.scalamock"          %% "scalamock"                       % "5.1.0"              % Test,
      guice
    ),
    wartremoverErrors in (Compile, compile) ++= Warts.unsafe,
    wartremoverExcluded += baseDirectory.value / "target",
    publishTo       := Some(Resolver.file("file", new File("/tmp"))),
    publishArtifact := false,
    majorRegexes    := Seq("\\[?breaking\\]?.*".r, "\\[?major\\]?.*".r),
    minorRegexes    := Seq("\\[?minor\\]?.*".r),
    bugfixRegexes   := Seq("\\[?bugfix\\]?.*".r, "\\[?fix\\]?.*".r, ".*".r),
    mappings in Universal ++= directory(
      baseDirectory.value / "src" / "main" / "resources"
    ),
    packageName in Docker := packageName.value,
    version in Docker     := version.value,
    dockerRepository      := Some("gastonschabas"),
    dockerBaseImage       := "adoptopenjdk:11-jre-openj9",
    dockerExposedPorts    := Seq(playPort),
    dockerLabels := Map(
      "maintainer" -> "gastonschabas@gmail.com",
      "app" -> name.value,
      "version" -> version.value
    ),
    dockerChmodType          := DockerChmodType.UserGroupWriteExecute,
    dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
  )
  .enablePlugins(PlayScala)
  .enablePlugins(DockerPlugin)
  .enablePlugins(JavaAppPackaging)
  .disablePlugins(PlayLayoutPlugin)
