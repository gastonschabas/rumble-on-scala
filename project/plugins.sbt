addSbtPlugin("org.scalameta"     %% "sbt-scalafmt"          % "2.5.2")
addSbtPlugin("org.scalastyle"    %% "scalastyle-sbt-plugin" % "1.0.0")
addSbtPlugin("org.scoverage"     %% "sbt-scoverage"         % "2.0.12")
addSbtPlugin("com.github.mwz"    %% "sbt-sonar"             % "2.2.0")
addSbtPlugin("org.playframework" %% "sbt-plugin"            % "3.0.4")
addSbtPlugin("com.github.sbt"     % "sbt-native-packager"   % "1.10.0")
addSbtPlugin("org.wartremover"    % "sbt-wartremover"       % "3.1.6")
addSbtPlugin("org.scala-sbt"      % "sbt-autoversion"       % "1.0.0")

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
