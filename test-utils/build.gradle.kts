plugins {
  `java-library`
  kotlin("jvm") version "1.8.10"
}

group = "de.pieroavola.kommons"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(11)
}
