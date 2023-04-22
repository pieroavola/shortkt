import java.net.URI

plugins {
  `java-library`
  `maven-publish`
  signing
  kotlin("jvm")
  id("org.jetbrains.dokka")
}

group = "de.pieroavola.shortkt"
version = "0.0.2"

java {

  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17

  withSourcesJar()
  withJavadocJar()

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

  val mavenUser: String by project
  val mavenPassword: String by project

  repositories {

    maven {
      name = "mavenCentralSnapshots"
      url = URI("https://s01.oss.sonatype.org/content/repositories/snapshots/")
      credentials {
        username = mavenUser
        password = mavenPassword
      }

      maven {
        name = "mavenCentral"
        url = URI("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
        credentials {
          username = mavenUser
          password = mavenPassword
        }
      }
    }
  }

  publications {
    create<MavenPublication>("utils") {

      from(components["java"])

      artifactId = "utils"

      pom {
        name.set("ShortKt Utils")
        description.set("Useful additions to the Kotlin standard library")
        url.set("https://github.com/pieroavola/shortkt")

        licenses {
          license {
            name.set("The Apache License, Version 2.0")
            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
          }
        }

        developers {
          developer {
            id.set("pavola")
            name.set("Piero Avola")
            email.set("me@pieroavola.de")
            organization.set("")
            organizationUrl.set("https://github.com/pieroavola/")
          }
        }

        scm {
          connection.set("scm:git:git://github.com/pieroavola/shortkt.git")
          developerConnection.set("scm:git:git://github.com/pieroavola/shortkt.git")
          url.set("https://github.com/pieroavola/shortkt/tree/main/utils")
        }
      }
    }
  }
}

signing {
  useGpgCmd()
  sign(publishing.publications["utils"])
}

tasks.named<Jar>("javadocJar") {

  dependsOn("dokkaJavadoc")
  from("$buildDir/dokka/javadoc")
  archiveClassifier.set("javadoc")
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(17)
}
