name := "asdf"

scalaVersion := "2.10.4"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

// Example imports.
libraryDependencies += "com.twitter" %% "finagle-thrift" % "6.24.0"
libraryDependencies += "com.twitter" %% "scrooge-core" % "3.17.0"
