name := """ThePlayBlog"""
organization := "be.vub"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies +="com.adrianhurt" %% "play-bootstrap" % "1.6.1-P28-B4"
libraryDependencies += "com.softwaremill.common" %% "id-generator" % "1.2.1"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "be.vub.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "be.vub.binders._"
