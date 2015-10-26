name := "asdf"

scalaVersion := "2.11.7"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

// Scala imports: usually double percent signs.
libraryDependencies += "com.twitter" %% "finagle-http" % "6.30.0"
libraryDependencies += "com.twitter" %% "util-logging" % "6.29.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
// Java imports: single %
libraryDependencies += "commons-io" % "commons-io" % "2.4"

scalaSource in Test := baseDirectory.value / "test"
