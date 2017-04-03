name := """play-java-testJob"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.mockito" % "mockito-all" % "1.10.19",
  "org.postgresql" % "postgresql" % "9.4.1208",
  "com.google.guava" % "guava" % "19.0",
  evolutions
)
