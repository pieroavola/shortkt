import java.net.URI

plugins {
  `java-library`
  `maven-publish`
  signing
  kotlin("jvm")
  id("org.jetbrains.dokka")
}

group = "de.pieroavola.shortkt"
version = "0.0.3"

java {

  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17

  withSourcesJar()
  withJavadocJar()
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test"))
  testImplementation("org.mockito:mockito-core:5.4.0")
  testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0") {
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
    create<MavenPublication>("testUtils") {

      from(components["java"])

      artifactId = "test-utils"

      pom {
        name.set("ShortKt Test-Utils")
        description.set("Useful utilities for testing in Kotlin")
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
          url.set("https://github.com/pieroavola/shortkt/tree/main/test-utils")
        }
      }
    }
  }
}

signing {
  useGpgCmd()
  sign(publishing.publications["testUtils"])
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
