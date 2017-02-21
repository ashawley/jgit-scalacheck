scalaVersion  := "2.11.8"

name := "jgit-scalacheck"

version := "0.1-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.8.8" % "test",
  "org.specs2" %% "specs2-mock" % "3.8.8" % "test",
  "org.specs2" %% "specs2-scalacheck" % "3.8.8" % "test",
  "org.eclipse.jgit" % "org.eclipse.jgit" % "4.6.0.201612231935-r",
  "org.eclipse.jgit" % "org.eclipse.jgit.junit" % "4.6.0.201612231935-r" % "test",
  // "com.jcraft" % "jsch" % "0.1.53",
  "org.slf4j" % "slf4j-log4j12" % "1.7.22" % "runtime",
  "org.slf4j" % "slf4j-nop" % "1.7.22" % "test", // See also [1] below
  "org.scala-sbt" %% "io" % "1.0.0-M9"
)

// 1. Favor slf4j-nop for Test over slf4j-log4j12 for Runtime
(dependencyClasspath in Test) <<= (dependencyClasspath in Test) map {
  _.filter {
    _.get(moduleID.key) exists (_.name != "slf4j-log4j12")
  }
}

// FIXME: Disk repository is not thread-safe.
// TODO: Use thread-safe, faster, in-memory repository for tests?
parallelExecution in Test := true
