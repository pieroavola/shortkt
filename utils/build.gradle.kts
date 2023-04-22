plugins {
  `java-library`
  `maven-publish`
  kotlin("jvm")
  id("org.jetbrains.dokka")
}

group = "de.pieroavola.shortkt"
version = "0.0.1-SNAPSHOT"

java {

  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17

  withSourcesJar()

  registerFeature("logging") {
    usingSourceSet(sourceSets["main"])
  }
}

fun DependencyHandlerScope.loggingApi(dependencyNotation: String): Dependency? {
  return "loggingApi"(dependencyNotation)
}

repositories {
  mavenCentral()
}

dependencies {

  loggingApi("org.slf4j:slf4j-api:2.0.6")
  loggingApi("org.slf4j:slf4j-simple:2.0.6")

  testImplementation(kotlin("test"))
  testImplementation(project(":test-utils"))
  testImplementation("org.mockito:mockito-inline:5.1.1")
  testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0") {
    exclude(module = "mockito-core")
  }
  testImplementation("org.assertj:assertj-core:3.24.2")
}

publishing {
  publications {
    create<MavenPublication>("utils") {
      from(components["java"])
    }
  }
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(17)
}
