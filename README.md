Rumble On Scala
===============

just a project to experiment with different things around scala ecosystem and other useful tools to build a project.

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=gastonschabas_rumble-on-scala&metric=coverage)](https://sonarcloud.io/dashboard?id=gastonschabas_rumble-on-scala)
[![Continious Delivery](https://github.com/gastonschabas/rumble-on-scala/workflows/Continious%20Delivery/badge.svg)](https://github.com/gastonschabas/rumble-on-scala/actions?query=workflow%3A%22Continious+Delivery%22)
[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-blue.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)

## Technology Stack Used

- **![Scala Icon](https://www.scala-lang.org/resources/favicon-16x16.png) [Scala](https://www.scala-lang.org/)**: The
Scala Programming Language combines object-oriented and functional programming in one concise, high-level language.
Scala's static types help avoid bugs in complex applications, and its JVM and JavaScript runtimes let you build
high-performance systems with easy access to huge ecosystems of libraries.

- **<img src="https://www.playframework.com/favicon.ico" width="16" height="16" />
[Play Framework](https://www.playframework.com/)**: Play Framework makes it easy to build web applications with Java &
Scala. Play is based on a lightweight, stateless, web-friendly architecture. Built on Akka, Play provides predictable
and minimal resource consumption (CPU, memory, threads) for highly-scalable applications.

- **![Slick Icon](http://scala-slick.org/doc/3.3.3/favicon.ico) [Slick](https://scala-slick.org/)**: Slick ("Scala
Language-Integrated Connection Kit") is Lightbend’s Functional Relational Mapping (FRM) library for Scala that makes it
easy to work with relational databases. It allows you to work with stored data almost as if you were using Scala
collections while at the same time giving you full control over when database access happens and which data is
transferred. You can also use SQL directly. Execution of database actions is done asynchronously, making Slick a perfect
fit for your reactive applications based on Play and Akka.

- **[Scala Test](https://www.scalatest.org/)**: ScalaTest is designed to increaste your team's productivity through
simple, clear tests and executable specifications that improve both code and communication.

- **<img src="https://www.testcontainers.org/favicon.ico" width="16" height="16" />
[Testcontainers](https://www.testcontainers.org/)**: Testcontainers is a Java library that supports JUnit tests,
providing lightweight, throwaway instances of common databases, Selenium web browsers, or anything else that can run in
a Docker container.

- **[random-data-generator](https://github.com/DanielaSfregola/random-data-generator)**: A library to generate random
data for test purposes, using ScalaCheck and scalacheck-shapeless.

## Topics Experimented

### Unit Test
- **[ScalaTest](https://www.scalatest.org/user_guide)**: is designed to increase your team's productivity through
simple, clear tests and executable specifications that improve both code and communication.

### Integration Test
- **[testcontainers-scala](https://github.com/testcontainers/testcontainers-scala)**: Scala wrapper for [testcontainers-java](https://github.com/testcontainers/testcontainers-java) that allows using docker containers for functional/integration/unit testing.

### Static code analyser 
- **[ScalaFmt](https://scalameta.org/scalafmt/)**: Formatter tool that can check is your code is well formatted,
also you can use it to auto format your code instead of just checking.
  
- **[Scapegoat](https://github.com/sksamuel/scapegoat)**: Scapegoat is a Scala static code analyzer, what is more
colloquially known as a code lint tool or linter. Scapegoat works in a similar vein to Java's FindBugs or checkstyle,
or Scala's Scalastyle.

- **[Scalastyle](http://www.scalastyle.org/)**: Scalastyle examines your Scala code and indicates potential problems
with it. If you have come across Checkstyle for Java, then you’ll have a good idea what scalastyle is. Except that
it’s for Scala obviously.

- **[Sonarcloud](https://sonarcloud.io/documentation)**: SonarCloud is a cloud-based code analysis service designed to
detect code quality issues in 25 different programming languages, continuously ensuring the maintainability,
reliability and security of your code.
Uploaded reports can be found [here](https://sonarcloud.io/dashboard?id=gastonschabas_rumble-on-scala)

### CI/CD 
- **[Github Action](https://docs.github.com/en/actions)**: Automate, customize, and execute your software development
workflows right in your repository with GitHub Actions. You can discover, create, and share actions to perform any job
you'd like, including CI/CD, and combine actions in a completely customized workflow.

  - Workflows:
    - [Continuous Integration](.github/workflows/ci.yml): will be triggered when a PR is opened against master. Unit
    tests, Integration tests and static code analyser will be executed, and the generated reports will be sent to
    sonarcloud. 
    - [Continuous Delivery](.github/workflows/cd.yml): it will be triggered each time new code is pushed to master.
    Unit tests, Integration tests and static code analyser will be executed, and the generated reports will be sent to
    sonarcloud. After that it will automatically deploy the new version to [heroku](https://www.heroku.com/).
    - [Scala Steward](https://scala-steward.org): Scala Steward is a bot that helps you keeping scala library
    dependencies and sbt plugins up-to-date. This workflow is scheduled to be triggered each Saturday at 9AM. 

### Deploy
After the CD workflow was executed, a new version of the project is deployed in https://rumble-on-scala.herokuapp.com/.
An HTTP GET request can be sent to https://rumble-on-scala.herokuapp.com/v0/hello

```shell script
curl https://rumble-on-scala.herokuapp.com/v0/hello
``` 