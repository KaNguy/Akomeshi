name := "Akomeshi"

version := "0.1"

scalaVersion := "2.13.6"

organization := "org.akomeshi"

versionScheme := Some("semver-spec")

homepage := Some(url("https://github.com/KaNguy/Akomeshi"))
licenses := Seq("Apache 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

publishMavenStyle := true
pomIncludeRepository := { _ => false }