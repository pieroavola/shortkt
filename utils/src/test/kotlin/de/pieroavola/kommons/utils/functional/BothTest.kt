package de.pieroavola.kommons.utils.functional

import de.pieroavola.kommons.test.data.ints
import de.pieroavola.kommons.test.data.strings
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class BothTest {

  //<editor-fold desc="Test: map and flatMap">

  @Test
  internal fun mapsLeftCorrectly() {

    val (left, right) = strings()

    val both = Both(left, right)
    val actualBoth = both.mapLeft {
      test(it)
    }

    assertThat(actualBoth).isEqualTo(Both(test(left), right))
  }

  @Test
  internal fun flatMapsLeftCorrectly() {

    val (left, right, ignored) = strings()

    val both = Both(left, right)
    val actualBoth = both.flatMapLeft {
      Both(test(it), ignored)
    }

    assertThat(actualBoth).isEqualTo(Both(test(left), right))
  }

  @Test
  internal fun mapsRightCorrectly() {

    val (left, right) = strings()

    val both = Both(left, right)
    val actualBoth = both.mapRight {
      test(it)
    }

    assertThat(actualBoth).isEqualTo(Both(left, test(right)))
  }

  @Test
  internal fun flatMapsRightCorrectly() {

    val (left, right, ignored) = strings()

    val both = Both(left, right)
    val actualBoth = both.flatMapRight {
      Both(ignored, test(right))
    }

    assertThat(actualBoth).isEqualTo(Both(left, test(right)))
  }

  //</editor-fold>

  //<editor-fold desc="Test: asPair">

  @Test
  internal fun convertsBothToPair() {

    val (left, right) = strings()

    val both = Both(left, right)

    assertThat(both.asPair()).isEqualTo(left to right)
  }

  //</editor-fold>

  //<editor-fold desc="Test: Deconstructing">

  @Test
  internal fun deconstructsCorrectly() {

    val (left) = strings()
    val (right) = ints()

    val (actualLeft, actualRight) = Both(left, right)

    assertThat(actualLeft).isEqualTo(left)
    assertThat(actualRight).isEqualTo(right)
  }

  //</editor-fold>

  //<editor-fold desc="Test: Equals">

  @Test
  internal fun equalWhenBothSidesEqual() {

    val (left, right) = strings().map { test(it) }

    val both1 = Both(left, right)
    val both2 = Both(left, right)

    assertThat(both1 == both2).isTrue()
  }

  @Test
  internal fun unequalWhenLeftDoesNotMatch() {

    val (left1, left2, right) = strings().map { test(it) }

    val both1 = Both(left1, right)
    val both2 = Both(left2, right)

    assertThat(both1 == both2).isFalse()
  }

  @Test
  internal fun unequalWhenRightDoesNotMatch() {

    val (left, right1, right2) = strings().map { test(it) }

    val both1 = Both(left, right1)
    val both2 = Both(left, right2)

    assertThat(both1 == both2).isFalse()
  }

  @Test
  internal fun unequalWhenNeitherSideMatches() {

    val (left1, left2, right1, right2) = strings().map { test(it) }

    val both1 = Both(left1, right1)
    val both2 = Both(left2, right2)

    assertThat(both1 == both2).isFalse()
  }

  //</editor-fold>

  //<editor-fold desc="Test: Hash Code">

  @Test
  internal fun hashCodeEqualWhenBothSidesEqual() {

    val (left, right) = strings().map { test(it) }

    val both1 = Both(left, right)
    val both2 = Both(left, right)

    assertThat(both1.hashCode() == both2.hashCode()).isTrue()
  }

  @Test
  internal fun hashCodeEnequalWhenLeftDoesNotMatch() {

    val (left1, left2, right) = strings().map { test(it) }

    val both1 = Both(left1, right)
    val both2 = Both(left2, right)

    assertThat(both1.hashCode() == both2.hashCode()).isFalse()
  }

  @Test
  internal fun hashCodeEnequalWhenRightDoesNotMatch() {

    val (left, right1, right2) = strings().map { test(it) }

    val both1 = Both(left, right1)
    val both2 = Both(left, right2)

    assertThat(both1.hashCode() == both2.hashCode()).isFalse()
  }

  @Test
  internal fun hashCodeEnequalWhenNeitherSideMatches() {

    val (left1, left2, right1, right2) = strings().map { test(it) }

    val both1 = Both(left1, right1)
    val both2 = Both(left2, right2)

    assertThat(both1.hashCode() == both2.hashCode()).isFalse()
  }

  //</editor-fold>

  //<editor-fold desc="Helper">

  private fun test(test: String): Any {
    data class Test(val test: String)
    return Test(test)
  }

  //</editor-fold>
}
