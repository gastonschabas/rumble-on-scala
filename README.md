Rumble On Scala
===============

just a project to experiment with different things around scala ecosystem and other useful tools to build a project.

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=gastonschabas_rumble-on-scala&metric=coverage)](https://sonarcloud.io/dashboard?id=gastonschabas_rumble-on-scala)
![Continious Delivery](https://github.com/gastonschabas/rumble-on-scala/workflows/Continious%20Delivery/badge.svg)

## Topics Experimented

### Unit Testing
- **ScalaTest**:

### Static code analyser 
- **[ScalaFmt](https://scalameta.org/scalafmt/)**: Formatter tool that can check is your code is well formatted,
also you can use it to auto format your code instead of just checking  
- **[Scapegoat](https://github.com/sksamuel/scapegoat)**: Scapegoat is a Scala static code analyzer, what is more
colloquially known as a code lint tool or linter. Scapegoat works in a similar vein to Java's FindBugs or checkstyle,
or Scala's Scalastyle.
- **[Scalastyle](http://www.scalastyle.org/)**: Scalastyle examines your Scala code and indicates potential problems
with it. If you have come across Checkstyle for Java, then you’ll have a good idea what scalastyle is. Except that
it’s for Scala obviously.
- **[Sonarcloud](https://sonarcloud.io/documentation)**: SonarCloud is a cloud-based code analysis service designed to
detect code quality issues in 25 different programming languages, continuously ensuring the maintainability,
reliability and security of your code.

### CI/CD 
- **[Github Action](https://docs.github.com/en/actions)**: Automate, customize, and execute your software development
workflows right in your repository with GitHub Actions. You can discover, create, and share actions to perform any job
you'd like, including CI/CD, and combine actions in a completely customized workflow.

  - There are two workflows:
    - [Continious Integration](.github/workflows/ci.yml): it will be triggered when a PR is opened against master.
    - [Continious Delivery](.github/workflows/cd.yml): it will be triggered each time the master branch reaceive a push.
