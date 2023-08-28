package de.pieroavola.shortkt.utils.cache

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant

class CachedTest {

  @Test
  internal fun cachesCorrectly() {

    var lastLoad = Instant.EPOCH
    var currentTime = Instant.now()
    var actualObject = ""

    val cache = Cached(Duration.ofSeconds(42), { currentTime }) {
      lastLoad = Instant.from(currentTime)
      actualObject
    }

    val delegate by cache

    actualObject = "hello"

    assertThat(delegate).isEqualTo(actualObject)
    assertThat(cache.value).isEqualTo(actualObject)
    assertThat(lastLoad).isEqualTo(currentTime)

    currentTime += Duration.ofSeconds(1)

    actualObject = "world"

    assertThat(delegate).isEqualTo("hello")
    assertThat(cache.value).isEqualTo("hello")
    assertThat(lastLoad).isEqualTo(currentTime - Duration.ofSeconds(1))

    cache.clear()

    assertThat(delegate).isEqualTo(actualObject)
    assertThat(cache.value).isEqualTo(actualObject)
    assertThat(lastLoad).isEqualTo(currentTime)

    actualObject = "!"
    currentTime += Duration.ofSeconds(41)

    assertThat(delegate).isEqualTo("world")
    assertThat(lastLoad).isEqualTo(currentTime - Duration.ofSeconds(41))

    currentTime += Duration.ofSeconds(1)

    assertThat(delegate).isEqualTo(actualObject)
    assertThat(lastLoad).isEqualTo(currentTime)
  }
}
