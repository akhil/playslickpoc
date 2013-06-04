import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "playslickpoc"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    ("com.typesafe.play" %% "play-slick" % "0.3.2").exclude("com.typesafe.slick", "slick_2.10"),
    "com.typesafe.slick" %% "slick" % "1.0.1",
    "org.json4s" %% "json4s-native" % "3.2.4"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here     
  )

}
