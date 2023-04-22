package de.pieroavola.shortkt.test.data

import java.util.UUID
import kotlin.random.Random

/**
 * Wrapper holding ten elements of a type, which can be accessed by destructuring.
 *
 * Objects can be created using the companion object's functions or by utilizing one of the helper fuctions for
 * generating random data.
 *
 * Example usage:
 * ```kotlin
 * @Test
 * fun printNumbers() {
 *   val (num1, num2, num3) = RandomDataProvider.from(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
 *   println(num1)
 *   println(num2)
 *   println(num3)
 * }
 * ```
 *
 * @see [RandomDataProvider.Companion.from]
 * @see [RandomDataProvider.Companion.generate]
 * @see [uuids]
 * @see [strings]
 * @see [ints]
 * @see [doubles]
 * @see [booleans]
 */
class RandomDataProvider<T> private constructor(
  private val data: List<T>
) {

  init {
    if (data.size != 10) {
      throw IllegalArgumentException("Provider must be initialized with 10 values")
    }
  }

  companion object {

    /**
     * Wrap a list into a [RandomDataProvider].
     *
     * @param data List containing ten elements.
     * @return [RandomDataProvider] containing the objects from the supplied list.
     *
     * @throws [IllegalArgumentException] if the list does not contain exactly ten elements.
     */
    fun <T> from(data: List<T>) = RandomDataProvider(data)

    /**
     * Wrap varargs into a [RandomDataProvider].
     *
     * @param data Ten arguments of a common type.
     * @return [RandomDataProvider] containing the objects from the supplied arguments.
     *
     * @throws [IllegalArgumentException] if not exactly ten elements are supplied.
     */
    fun <T> from(vararg data: T) = RandomDataProvider(data.toList())

    /**
     * Wrap the return values of a lambda into a [RandomDataProvider].
     *
     * @param generator Function that is called ten times to calculate the data.
     * @return [RandomDataProvider] containing the ten returned results of the generator function.
     */
    fun <T> generate(generator: () -> T) = RandomDataProvider((0..9).map { generator() })
  }

  /** @suppress */
  operator fun component1(): T = data[0]

  /** @suppress */
  operator fun component2(): T = data[1]

  /** @suppress */
  operator fun component3(): T = data[2]

  /** @suppress */
  operator fun component4(): T = data[3]

  /** @suppress */
  operator fun component5(): T = data[4]

  /** @suppress */
  operator fun component6(): T = data[5]

  /** @suppress */
  operator fun component7(): T = data[6]

  /** @suppress */
  operator fun component8(): T = data[7]

  /** @suppress */
  operator fun component9(): T = data[8]

  /** @suppress */
  operator fun component10(): T = data[9]

  /**
   * Maps each data point.
   *
   * @param mappingFunction Function applied to each value contained in the [RandomDataProvider]
   * @return A new [RandomDataProvider] with the mapped values.
   */
  fun <R> map(mappingFunction: (T) -> R) = from(data.map(mappingFunction))

  /**
   * Convert the [RandomDataProvider] to a [List].
   *
   * @return Unmodifiable [List] containing all values of the [RandomDataProvider] in order.
   */
  fun toList() = data.toList()

  /**
   * Convert the [RandomDataProvider] to a [Set].
   *
   * @return Unmodifiable [Set] containing all values of the [RandomDataProvider].
   */
  fun toSet() = data.toSet()

  /** @suppress */
  override fun toString() = data.toString()
}

/**
 * Generate a [RandomDataProvider] containing random objects of type [UUID].
 */
fun uuids(): RandomDataProvider<UUID> = RandomDataProvider.generate { UUID.randomUUID() }

/**
 * Generate a [RandomDataProvider] containing random objects of type [String].
 *
 * The strings are UUIDs with the minuses removed.
 */
fun strings(): RandomDataProvider<String> =
  RandomDataProvider.generate { UUID.randomUUID().toString().replace("-", "") }

/**
 * Generate a [RandomDataProvider] containing random objects of type [Int].
 */
fun ints(): RandomDataProvider<Int> = RandomDataProvider.generate { Random.nextInt() }

/**
 * Generate a [RandomDataProvider] containing random objects of type [Double].
 */
fun doubles(): RandomDataProvider<Double> = RandomDataProvider.generate { Random.nextDouble() }

/**
 * Generate a [RandomDataProvider] containing random objects of type [Boolean].
 */
fun booleans(): RandomDataProvider<Boolean> = RandomDataProvider.generate { Random.nextBoolean() }
