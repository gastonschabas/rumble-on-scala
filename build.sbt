import com.typesafe.sbt.SbtNativePackager.autoImport.NativePackagerHelper._
import com.typesafe.sbt.packager.docker.{
  DockerChmodType,
  DockerPermissionStrategy
}

ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / organization     := "com.gaston"
ThisBuild / organizationName := "gaston-schabas"
ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always

licenses += ("GPL-3.0", url(
  "https://github.com/gastonschabas/rumble-on-scala/blob/master/LICENSE"
))

lazy val testContainerVersion = "0.41.0"
lazy val playPort = 9000

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    name := "rumble-on-scala",
    resolvers += Resolver.jcenterRepo,
    libraryDependencies ++= Seq(
      "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % "it, test",
      "org.playframework"      %% "play-slick"         % "6.0.0",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.16.0",
      "com.dimafeng" %% "testcontainers-scala-scalatest" % testContainerVersion % "it",
      "com.dimafeng" %% "testcontainers-scala-postgresql" % testContainerVersion % "it",
      "org.postgresql"       % "postgresql"            % "42.6.0",
      "com.danielasfregola" %% "random-data-generator" % "2.9" % "it, test",
      "io.github.nafg.slick-migration-api" %% "slick-migration-api-flyway" % "0.10.0",
      "org.scalamock" %% "scalamock" % "5.2.0" % Test,
      guice
    ),
    coverageExcludedPackages := ".*Reverse.*;.*Routes.*",
    Compile / compile / wartremoverErrors ++= Warts.unsafe,
    wartremoverExcluded += baseDirectory.value / "target",
    publishTo       := Some(Resolver.file("file", new File("/tmp"))),
    publishArtifact := false,
    majorRegexes    := Seq("\\[?breaking\\]?.*".r, "\\[?major\\]?.*".r),
    minorRegexes    := Seq("\\[?minor\\]?.*".r),
    bugfixRegexes   := Seq("\\[?bugfix\\]?.*".r, "\\[?fix\\]?.*".r, ".*".r),
    Universal / mappings ++= directory(
      baseDirectory.value / "src" / "main" / "resources"
    ),
    Docker / packageName := packageName.value,
    Docker / version     := version.value,
    dockerRepository     := Some("gastonschabas"),
    dockerBaseImage      := "amazoncorretto:17-alpine-jdk",
    dockerExposedPorts   := Seq(playPort),
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
