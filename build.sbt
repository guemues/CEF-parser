name := "CEFparser"
organization := "io.tazi"
scalaVersion := "2.11.8"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
libraryDependencies += "com.typesafe" % "config" % "1.3.0"
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.6" % "test"
libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"

version := "0.0.1-SNAPSHOT"

// Tazi
addCommandAlias("tzRelease", "; set publishTo := Some(\"Artifactory Realm\" at \"https://repo.tazi.io/artifactory/libs-release-intern/\") ; publish") ++
  addCommandAlias("tzSnapshot", "; set publishTo := Some(\"Artifactory Realm\" at \"https://repo.tazi.io/artifactory/libs-snapshot-intern/\") ; publish")

publishTo := {
  val nexus = "https://repo.tazi.io/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "artifactory/libs-snapshot-intern;build.timestamp=" + (new java.util.Date().getTime))
  else
    Some("releases"  at nexus + "artifactory/libs-release-intern/")
}

//publishTo := Some("Artifactory Realm" at "https://repo.tazi.io/artifactory/libs-release-local/")

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

resolvers += "tazi-releases" at "https://repo.tazi.io/artifactory/libs-release-intern/"
resolvers += Resolver.sonatypeRepo("snapshots")
//!Tazi

