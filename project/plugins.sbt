// Comment to get more information during initialization
logLevel := Level.Warn

// The repositories
resolvers ++= Seq(
   "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
   "DEFRA Nexus - releases" at "https://defranexus.kainos.com/content/repositories/releases/"
)

resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

credentials += Credentials("Sonatype Nexus Repository Manager", "defranexus.kainos.com", "admin", "cap-del1very")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.4.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.8.8")

addSbtPlugin("com.github.retronym" % "sbt-onejar" % "0.8")

// Plugin that provides a customizable release process compatible with hubflow.
addSbtPlugin("com.capd" % "sbt-hubflow" % "0.1.4")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")