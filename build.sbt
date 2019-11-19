name := """shopping-store"""
organization := "com.spendit"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.0"

val playSlickVersion = "5.0.0-M5"
val postgreSqlVersion = "42.2.6"
val hikariVersion = "3.3.1"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % playSlickVersion,
  "com.typesafe.play" %% "play-slick-evolutions" % playSlickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % hikariVersion,
  "org.postgresql" % "postgresql" % postgreSqlVersion,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test,
  guice)
