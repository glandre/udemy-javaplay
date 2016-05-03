 name := """consulting-services-inc"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava,PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaCore,
  cache,
  jdbc,
  evolutions,
  ws,
  "org.webjars" %% "webjars-play" % "2.4.0",
  "org.webjars" % "bootstrap" % "3.3.6",
  "com.adrianhurt" %% "play-bootstrap" % "1.1-P25-B3-SNAPSHOT",
  filters
)

resolvers ++= Seq(
	"Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
	"Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/",
	"Apache" at "http://repo1.maven.org/maven2/"
)