name := "scalaPlayPractice"

lazy val `scalaplaypractice` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  evolutions,
  jdbc,
  cache,
  ws,
  "com.typesafe.play" %% "anorm" % "2.5.1",
  "mysql" % "mysql-connector-java" % "5.1.18",
  specs2 % Test
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"