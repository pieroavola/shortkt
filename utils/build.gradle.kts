plugins {
  `java-library`
  kotlin("jvm")
  id("org.jetbrains.dokka")
}

group = "de.pieroavola.kommons"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {

  implementation("org.slf4j:slf4j-api:2.0.6")
  implementation("org.slf4j:slf4j-simple:2.0.6")

  testImplementation(kotlin("test"))
  testImplementation(project(":test-utils"))
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
