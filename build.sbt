import Dependencies._

ThisBuild / scalaVersion     := "2.13.3"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

val awsVersion = "1.11.895"
val circeVersion = "0.13.0"

lazy val root = (project in file("."))
  .settings(
    name := "aws-opted-in-regions",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "com.amazonaws" % "aws-java-sdk-iam" % awsVersion,
      "com.amazonaws" % "aws-java-sdk-ec2" % awsVersion,
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "com.github.scopt" %% "scopt" % "4.0.0",
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
