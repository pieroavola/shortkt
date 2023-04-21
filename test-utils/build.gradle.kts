plugins {
  `java-library`
  kotlin("jvm") version "1.8.20"
  id("org.jetbrains.dokka")
}

group = "de.pieroavola.kommons"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test"))
  testImplementation("org.mockito:mockito-inline:5.1.1")
  testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0") {
    exclude(module = "mockito-core")
  }
  testImplementation("org.assertj:assertj-core:3.24.2")
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(17)
}
