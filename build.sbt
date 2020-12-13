import com.typesafe.sbt.SbtNativePackager.autoImport.NativePackagerHelper._
import com.typesafe.sbt.packager.docker.{
  DockerChmodType,
  DockerPermissionStrategy
}

ThisBuild / scalaVersion     := "2.13.4"
ThisBuild / organization     := "com.gaston"
ThisBuild / organizationName := "gaston-schabas"

inThisBuild(
  List(
    // These are normal sbt settings to configure for release, skip if already defined
    licenses := Seq(
      "GPL-3.0" -> url(
        "https://github.com/gastonschabas/rumble-on-scala/blob/master/LICENSE"
      )
    ),
    homepage := Some(url("https://github.com/gastonschabas/rumble-on-scala")),
    developers := List(
      Developer(
        "gastonschabas",
        "Gastón Schabas",
        "gastonschabas@gmail.com",
        url("https://github.com/gastonschabas/rumble-on-scala")
      )
    ),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/gastonschabas/rumble-on-scala/"),
        "scm:git:git@github.com:gastonschabas/rumble-on-scala.git"
      )
    ),
    // These are the sbt-release-early settings to configure
    pgpPublicRing    := file("/keys/.gnupg/pubring.asc"),
    pgpSecretRing    := file("/keys/.gnupg/secring.asc"),
    releaseEarlyWith := SonatypePublisher
  )
)

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
      "org.scalamock"          %% "scalamock"                       % "5.1.0"              % Test,
      guice
    ),
    wartremoverErrors in (Compile, compile) ++= Warts.unsafe,
    wartremoverExcluded += baseDirectory.value / "target",
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
