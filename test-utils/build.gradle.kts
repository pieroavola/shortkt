plugins {
  `java-library`
  `maven-publish`
  kotlin("jvm") version "1.8.20"
  id("org.jetbrains.dokka")
}

group = "de.pieroavola.kommons"
version = "0.0.1-SNAPSHOT"

java {

  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17

  withSourcesJar()
}

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

publishing {
  publications {
    create<MavenPublication>("test-utils") {
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
