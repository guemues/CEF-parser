name := "CEFparser"
scalaVersion := "2.11.8"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
libraryDependencies += "com.typesafe" % "config" % "1.3.0"
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.6" % "test"

version := "0.0.1-SNAPSHOT"

// Tazi
addCommandAlias("tzRelease", "; set publishTo := Some(\"Artifactory Realm\" at \"https://repo.tazi.io/artifactory/libs-release-local/\") ; publish") ++
  addCommandAlias("tzSnapshot", "; set publishTo := Some(\"Artifactory Realm\" at \"https://repo.tazi.io/artifactory/libs-snapshot-local/\") ; publish")

publishTo := {
  val nexus = "https://repo.tazi.io/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "artifactory/libs-snapshot-local;build.timestamp=" + (new java.util.Date().getTime))
  else
    Some("releases"  at nexus + "artifactory/libs-release-local/")
}

//publishTo := Some("Artifactory Realm" at "https://repo.tazi.io/artifactory/libs-release-local/")

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

resolvers += "tazi-releases" at "https://repo.tazi.io/artifactory/libs-release-local/"
resolvers += Resolver.sonatypeRepo("snapshots")
//!Tazi

