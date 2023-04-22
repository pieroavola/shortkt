package de.pieroavola.shortkt.utils.functional

import de.pieroavola.shortkt.test.data.strings
import de.pieroavola.shortkt.test.data.uuids
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class BothExtensionsTest {

  //<editor-fold desc="Test: and">

  @Test
  internal fun andReturnsBoth() {

    val (left, right) = strings()

    assertThat(left and right).isEqualTo(Both(left, right))
  }

  @Test
  internal fun andWithLeftBeingNull() {

    val left: String? = null
    val (right) = strings()

    assertThat(left and right).isEqualTo(Both<String?, String>(null, right))
  }

  @Test
  internal fun andWithRightBeingNull() {

    val (left) = strings()
    val right: String? = null

    assertThat(left and right).isEqualTo(Both<String, String?>(left, null))
  }

  //</editor-fold>

  //<editor-fold desc="Test: asBoth">

  @Test
  internal fun convertsPairToBoth() {

    val (left, right) = strings()

    val pair = left to right

    assertThat(pair.asBoth()).isEqualTo(Both(pair.first, pair.second))
  }

  //</editor-fold>

  //<editor-fold desc="Test: Iterable">

  @Test
  internal fun mapLeftOnIterable() {

    val original: List<Both<String, String>> =
      uuids().map { Both(it.toString(), it.toString().replace("-", "")) }.toList()

    val actualList = original.mapLeft { test(it) }

    assertThat(actualList).containsExactlyElementsOf(original.map { Both(test(it.left), it.right) })
  }

  @Test
  internal fun flatMapLeftOnIterable() {

    val original: List<Both<String, String>> =
      uuids().map { Both(it.toString(), it.toString().replace("-", "")) }.toList()

    val actualList = original.flatMapLeft { Both(test(it), 42) }

    assertThat(actualList).containsExactlyElementsOf(original.map { Both(test(it.left), it.right) })
  }

  @Test
  internal fun mapRightOnIterable() {

    val original: List<Both<String, String>> =
      uuids().map { Both(it.toString(), it.toString().replace("-", "")) }.toList()

    val actualList = original.mapRight { test(it) }

    assertThat(actualList).containsExactlyElementsOf(original.map { Both(it.left, test(it.right)) })
  }

  @Test
  internal fun flatMapRightOnIterable() {

    val original: List<Both<String, String>> =
      uuids().map { Both(it.toString(), it.toString().replace("-", "")) }.toList()

    val actualList = original.flatMapRight { Both(42, test(it)) }

    assertThat(actualList).containsExactlyElementsOf(original.map { Both(it.left, test(it.right)) })
  }

  //</editor-fold>

  //<editor-fold desc="Test: Sequence">

  @Test
  internal fun mapLeftOnSequence() {

    var counter = 0

    val actualResult = (1..10)
      .toList()
      .map { Both(it, it) }
      .asSequence()
      .mapLeft {
        counter++
        it * 10
      }
      .takeWhile { it.right < 4 }
      .toList()
      .map { it.left }

    assertThat(actualResult).containsExactly(10, 20, 30)
    assertThat(counter).isEqualTo(4)
  }

  @Test
  internal fun flatMapLeftOnSequence() {

    var counter = 0

    val actualResult = (1..10)
      .toList()
      .map { Both(it, it) }
      .asSequence()
      .flatMapLeft {
        counter++
        Both(it * 10, "42")
      }
      .takeWhile { it.right < 4 }
      .toList()
      .map { it.left }

    assertThat(actualResult).containsExactly(10, 20, 30)
    assertThat(counter).isEqualTo(4)
  }

  @Test
  internal fun mapRightOnSequence() {

    var counter = 0

    val actualResult = (1..10)
      .toList()
      .map { Both(it, it) }
      .asSequence()
      .mapRight {
        counter++
        it * 10
      }
      .takeWhile { it.left < 4 }
      .toList()
      .map { it.right }

    assertThat(actualResult).containsExactly(10, 20, 30)
    assertThat(counter).isEqualTo(4)
  }

  @Test
  internal fun flatMapRightOnSequence() {

    var counter = 0

    val actualResult = (1..10)
      .toList()
      .map { Both(it, it) }
      .asSequence()
      .flatMapRight {
        counter++
        Both("42", it * 10)
      }
      .takeWhile { it.left < 4 }
      .toList()
      .map { it.right }

    assertThat(actualResult).containsExactly(10, 20, 30)
    assertThat(counter).isEqualTo(4)
  }

  //</editor-fold>

  //<editor-fold desc="Test: Map">

  @Test
  internal fun createMap() {

    val (k1, k2, k3, v1, v2, v3) = strings()

    val both1 = Both(k1, v1)
    val both2 = Both(k2, v2)
    val both3 = Both(k3, v3)

    val map = mapOf(both1, both2, both3)

    assertThat(map.entries)
      .containsExactlyInAnyOrderElementsOf(mapOf(both1.asPair(), both2.asPair(), both3.asPair()).entries)
  }

  @Test
  internal fun createMutableMap() {

    val (k1, k2, k3, v1, v2, v3) = strings()

    val both1 = Both(k1, v1)
    val both2 = Both(k2, v2)
    val both3 = Both(k3, v3)

    val map = mutableMapOf(both1, both2, both3)
    map["42"] = "42"

    assertThat(map.entries).containsExactlyInAnyOrderElementsOf(
      mutableMapOf(both1.asPair(), both2.asPair(), both3.asPair(), "42" to "42").entries
    )
  }

  @Test
  internal fun allEntriesAsBoths() {

    val (k1, k2, k3, v1, v2, v3) = strings()

    val map = mapOf(
      k1 to v1,
      k2 to v2,
      k3 to v3,
    )

    assertThat(map.boths).containsExactlyInAnyOrder(Both(k1, v1), Both(k2, v2), Both(k3, v3))
  }

  //</editor-fold>

  //<editor-fold desc="Helper">

  private fun test(test: String): Any {
    data class Test(val test: String)
    return Test(test)
  }

  //</editor-fold>
}
