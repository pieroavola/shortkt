package de.pieroavola.shortkt.test.data

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID

class RandomDataProviderTest {

  @Test
  internal fun destructuresListIntoElementsOfList() {

    val list = (0..9).map { UUID.randomUUID() }

    RandomDataProvider.from(list).destructuresList(list)
  }

  @Test
  internal fun destructuresVarargsIntoElementsOfList() {

    val list = (0..9).map { UUID.randomUUID() }

    RandomDataProvider.from(*list.toTypedArray()).destructuresList(list)
  }

  @Test
  internal fun destructuresGeneratedValues() {

    val list = (0..9).map { UUID.randomUUID() }
    var count = 0

    fun nextElement() = list[count++]

    RandomDataProvider.generate { nextElement() }.destructuresList(list)
  }

  @Test
  internal fun mapsCorrectly() {

    val list = (0..9).map { UUID.randomUUID() }
    val mappingFunction: (UUID) -> String = { it.toString().replace("-", "") }

    RandomDataProvider.from(list).map(mappingFunction).destructuresList(list.map(mappingFunction))
  }

  @Test
  internal fun convertsToList() {

    val list = (0..9).map { UUID.randomUUID() }

    assertThat(RandomDataProvider.from(list).toList()).containsExactlyElementsOf(list)
  }

  @Test
  internal fun convertsToSet() {

    val list = (0..9).map { UUID.randomUUID() }

    assertThat(RandomDataProvider.from(list).toSet()).containsExactlyInAnyOrderElementsOf(list)
  }

  @Test
  internal fun serializesToString() {

    val list = (0..9).map { UUID.randomUUID() }

    assertThat(RandomDataProvider.from(list).toString()).isEqualTo(list.toString())
  }
}

private fun <T> RandomDataProvider<T>.destructuresList(list: List<T>) {

  val (
    component1,
    component2,
    component3,
    component4,
    component5,
    component6,
    component7,
    component8,
    component9,
    component10,
  ) = this

  assertThat(component1).isEqualTo(list[0])
  assertThat(component2).isEqualTo(list[1])
  assertThat(component3).isEqualTo(list[2])
  assertThat(component4).isEqualTo(list[3])
  assertThat(component5).isEqualTo(list[4])
  assertThat(component6).isEqualTo(list[5])
  assertThat(component7).isEqualTo(list[6])
  assertThat(component8).isEqualTo(list[7])
  assertThat(component9).isEqualTo(list[8])
  assertThat(component10).isEqualTo(list[9])
}
