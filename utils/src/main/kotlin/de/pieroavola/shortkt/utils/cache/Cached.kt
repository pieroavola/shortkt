package de.pieroavola.shortkt.utils.cache

import java.time.Instant
import java.time.temporal.TemporalAmount
import java.util.concurrent.atomic.AtomicReference
import kotlin.reflect.KProperty

/**
 * Represents the cached result of a computation.
 *
 * To create an instance of [Cached] use the [cached] function.
 */
class Cached<T> internal constructor(
  private val validity: TemporalAmount?,
  private val currentTimeSupplier: () -> Instant,
  private val computation: () -> T,
) {

  internal constructor(validity: TemporalAmount?, loader: () -> T) : this(validity, Instant::now, loader)

  private var cachedValue: AtomicReference<T>? = null
  private var lastUpdate: Instant? = null

  /**
   * Returns the cached value. If the value has not been computed yet or a time greater than the validity has passed,
   * the value will be computed first.
   */
  val value: T
    get() {

      if (cachedValue == null || lastUpdate?.isAfter(currentTimeSupplier().minus(validity)) != true) {
        reload()
      }

      return cachedValue?.get() ?: throw ConcurrentModificationException()
    }

  /**
   * This function allows the value of an instance of [Cached] to be delegated to a read only property of type [T].
   *
   * Usage: `val cachedString: String by cached { "cached!" }`
   */
  operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

  /**
   * Clears the cache. This will result in the value being re-computed on the next read.
   */
  fun clear() {
    cachedValue = null
    lastUpdate = null
  }

  override fun toString(): String {

    val v = cachedValue
    val t = lastUpdate

    if (v == null || t == null) {
      return "Cached[]"
    }

    return "Cached[value=${v.get()}; cached=${t}; valid_until=${t.plus(validity)}]"
  }

  private fun reload() {
    cachedValue = AtomicReference(computation())
    lastUpdate = currentTimeSupplier()
  }
}

/**
 * Returns an instance of [Cached] which can be used to cache computation results.
 *
 * @param validity Specifies how long to wait until re-computing the value. If `null` the value will only be
 *   re-computed after manually clearing the cache.
 * @param computation Instructions to compute the cached value.
 */
fun <T> cached(validity: TemporalAmount? = null, computation: () -> T) = Cached(validity, computation)
