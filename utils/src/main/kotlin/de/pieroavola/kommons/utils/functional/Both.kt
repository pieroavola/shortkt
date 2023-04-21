package de.pieroavola.kommons.utils.functional

import java.io.Serializable
import java.util.Objects

/**
 * Represents a combination of two generic values similar to [Pair], adding convenience functions for functional style
 * code.
 *
 * While like [Pair] objects of this class can be used for any purpose, the main use case is for transforming data while
 * maintaining the original objects (usually on the left).
 *
 * @param L Type of the left value.
 * @param R Type of the right value.
 * @property left Left value.
 * @property right Right value.
 * @constructor Creates a new instance of [Both].
 */
class Both<out L, out R>(
  val left: L,
  val right: R,
) : Serializable {

  /**
   * Returns a new instance of [Both] containing the result of applying the given transform on the left side.
   *
   * @param transform Transformation function to be applied on the left value.
   * @return A new instance of [Both] with transformed left side.
   */
  fun <X> mapLeft(transform: (L) -> X): Both<X, R> = Both(transform(left), right)

  /**
   * Returns a new instance of [Both] containing the result of applying the given transform on the right side.
   *
   * @param transform Transformation function to be applied on the right value.
   * @return A new instance of [Both] with transformed right side.
   */
  fun <X> mapRight(transform: (R) -> X): Both<L, X> = Both(left, transform(right))

  /**
   * Returns a new instance of [Both] containing the result of applying the given transform on the left side. The left
   * value is transformed to an instance of [Both], of which the left side is taken.
   *
   * @param transform Transformation function to be applied on the left value to an instance of [Both].
   * @return A new instance of [Both] with transformed left side.
   */
  fun <X> flatMapLeft(transform: (L) -> Both<X, *>): Both<X, R> = Both(transform(left).left, right)

  /**
   * Returns a new instance of [Both] containing the result of applying the given transform on the right side. The right
   * value is transformed to an instance of [Both], of which the right side is taken.
   *
   * @param transform Transformation function to be applied on the right value to an instance of [Both].
   * @return A new instance of [Both] with transformed right side.
   */
  fun <X> flatMapRight(transform: (R) -> Both<*, X>): Both<L, X> = Both(left, transform(right).right)

  /**
   * Converts an instance [Both] to an instance of [Pair] with the same values
   */
  fun asPair() = Pair(left, right)

  /** @suppress */
  operator fun component1(): L = left

  /** @suppress */
  operator fun component2(): R = right

  /** @suppress */
  override fun toString() = "($left | $right)"

  /** @suppress */
  override fun equals(other: Any?) =
    other is Both<*, *> && left == other.left && right == other.right

  /** @suppress */
  override fun hashCode(): Int = Objects.hash(left, right)
}
