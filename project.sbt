name := "asdf"

scalaVersion := "2.10.4"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers += "Sonatype Public" at "https://oss.sonatype.org/content/groups/public"
resolvers += "maven.twttr" at "http://maven.twttr.com/"

libraryDependencies += "com.twitter" %% "scrooge-core" % "3.17.0"
libraryDependencies += "org.apache.thrift" % "libthrift" % "0.8.0"


// This 'val' sets the inConfig settings.
// inConfig(Compile)(genThriftSettings)
com.twitter.scrooge.ScroogeSBT.newSettings

inConfig(Compile)(com.twitter.scrooge.ScroogeSBT.scroogeThriftSources := Seq(baseDirectory.value / "test.thrift"))
// com.twitter.scrooge.ScroogeSBT.scroogeThriftOutputFolder := baseDirectory.value
// com.twitter.scrooge.ScroogeSBT.scroogeThriftSources := Seq(baseDirectory.value / "test.thrift")
