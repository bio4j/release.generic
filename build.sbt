name          := "release.generic"
organization  := "bio4j"
description   := "generic bio4j data import"

bucketSuffix  := "era7.com"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq (
  "bio4j"                   % "bio4j"              % "0.12.0-227-g60cce98",
  "bio4j"                  %% "data-uniprot"       % "0.1.1",
  "org.scala-lang.modules" %% "scala-xml"          % "1.0.6",
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",
  "ohnosequences"          %% "fastarious"         % "0.6.0",
  "ohnosequences"          %% "statika"            % "2.0.0-M5",
  "org.scalatest"          %% "scalatest"          % "2.2.6" % Test
)

dependencyOverrides := Set (
  "org.scala-lang.modules" %% "scala-xml"     % "1.0.5",
  // "org.scala-lang"         %  "scala-library" % "2.11.8",
  "com.github.pathikrit"   %% "better-files"  % "2.16.0"
)

wartremoverErrors in (Compile, compile) := Seq()
// wartremoverExcluded ++= Seq(
//   baseDirectory.value/"src"/"main"/"scala"/"uniprot"/"uniprotEntry.scala",
//   baseDirectory.value/"src"/"test"/"scala"/"ncbiTaxonomy.scala"
// )

generateStatikaMetadataIn(Compile)

// This turns on fat-jar publishing during release process:
publishFatArtifact in Release := true
