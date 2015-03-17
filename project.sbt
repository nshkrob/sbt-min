name := "asdf"

scalaVersion := "2.10.4"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers += "Sonatype Public" at "https://oss.sonatype.org/content/groups/public"
resolvers += "maven.twttr" at "http://maven.twttr.com/"

// Example imports.
// libraryDependencies += "com.twitter" %% "finagle-thrift" % "6.24.0"
// libraryDependencies += "com.twitter" %% "scrooge-core" % "3.17.0"



com.twitter.scrooge.ScroogeSBT.newSettings

com.twitter.scrooge.ScroogeSBT.scroogeBuildOptions := Seq("--finagle")

com.twitter.scrooge.ScroogeSBT.scroogeThriftSourceFolder := baseDirectory.value
// com.twitter.scrooge.ScroogeSBT.scroogeThriftOutputFolder := baseDirectory.value
// com.twitter.scrooge.ScroogeSBT.scroogeThriftSources := Seq(baseDirectory.value / "test.thrift")
