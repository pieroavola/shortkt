package de.pieroavola.kommons.test.data

import java.util.UUID
import kotlin.random.Random

class RandomDataProvider<T> private constructor(
  private val data: List<T>
) {

  init {
    if (data.size != 10) {
      throw IllegalArgumentException("Provider must be initialized with 10 values")
    }
  }

  companion object {

    fun <T> from(data: List<T>) = RandomDataProvider(data)
    fun <T> from(vararg data: T) = RandomDataProvider(data.toList())
    fun <T> generate(generator: () -> T) = RandomDataProvider((0..9).map { generator() })
  }

  operator fun component1(): T = data[0]
  operator fun component2(): T = data[1]
  operator fun component3(): T = data[2]
  operator fun component4(): T = data[3]
  operator fun component5(): T = data[4]
  operator fun component6(): T = data[5]
  operator fun component7(): T = data[6]
  operator fun component8(): T = data[7]
  operator fun component9(): T = data[8]
  operator fun component10(): T = data[9]

  fun <R> map(mappingFunction: (T) -> R) = from(data.map(mappingFunction))
  fun toList() = data.toList()
  fun toSet() = data.toSet()
  override fun toString() = data.toString()
}

fun uuids(): RandomDataProvider<UUID> = RandomDataProvider.generate { UUID.randomUUID() }
fun strings(): RandomDataProvider<String> =
  RandomDataProvider.generate { UUID.randomUUID().toString().replace("-", "") }
fun ints(): RandomDataProvider<Int> = RandomDataProvider.generate { Random.nextInt() }
fun doubles(): RandomDataProvider<Double> = RandomDataProvider.generate { Random.nextDouble() }
fun booleans(): RandomDataProvider<Boolean> = RandomDataProvider.generate { Random.nextBoolean() }
