import sbt._
import sbt.Keys._
import sbtassembly.Plugin._
import AssemblyKeys._
import com.capd.sbthubflow.ReleasePlugin._

object ApplicationBuild extends Build {

  lazy val apiDependencies = Seq(
  )

  lazy val serviceDependencies = Seq(
    "io.dropwizard" % "dropwizard-core" % "0.7.1",
    "io.dropwizard" % "dropwizard-auth" % "0.7.1",
    "io.dropwizard" % "dropwizard-assets" % "0.7.1",
    "io.dropwizard" % "dropwizard-views-freemarker" % "0.7.1",
    "io.dropwizard" % "dropwizard-views-mustache" % "0.7.1",
    "io.dropwizard" % "dropwizard-client" % "0.7.1",
    "io.dropwizard" % "dropwizard-testing" % "0.7.1"
  )

  val appReleaseSettings = releaseSettings ++ Seq(
    // Publishing options:
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { x => false },
    publishTo <<= version { (v: String) =>
      val nexus = "https://defranexus.kainos.com/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("sonatype-snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("sonatype-releases"  at nexus + "content/repositories/releases")
    },
    credentials += Credentials("Sonatype Nexus Repository Manager", "defranexus.kainos.com", "admin", "cap-del1very")
  )

  def defaultResolvers = Seq(
    "DEFRA Nexus Release repo" at "https://defranexus.kainos.com/content/repositories/releases/",
    "DEFRA third party repo" at "https://defranexus.kainos.com/content/repositories/thirdparty/"
  )


  def commonSettings = Defaults.defaultSettings ++ Seq(
    organization := "uk.gov.defra",
    autoScalaLibrary := false,
    crossPaths := false,
    exportJars := false,
    scalaVersion := "2.10.2",
    resolvers ++= defaultResolvers
  )

  def standardSettingsWithAssembly = commonSettings ++ assemblySettings ++ appReleaseSettings ++ Seq(
    mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
    {
      case "about.html" => MergeStrategy.rename
      case x => old(x)
    }
  })

  lazy val root = Project("capd-buildstatus", file("."), settings = Defaults.defaultSettings ++ appReleaseSettings ++ Seq(
    name := "uk.gov.defra.capd.buildstatus",
    resolvers ++= defaultResolvers
  )) aggregate(DataloadService)

  lazy val DataloadService: Project = Project("buildstatus-service", file("buildstatus-service"),
    settings = standardSettingsWithAssembly ++ Seq(
      name := "uk.gov.defra.capd.buildstatus.service",
      libraryDependencies ++= serviceDependencies
    ))
}