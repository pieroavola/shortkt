package de.pieroavola.kommons.utils.functional

/**
 * Creates a tuple of type [Both] from this and [that].
 */
infix fun <L, R> L.and(that: R) = Both(this, that)

/**
 * Returns a list containing the result of applying the given transform to the left value of each element in the
 * original collection.
 *
 * @see [Both.mapLeft]
 */
fun <L, R, X> Iterable<Both<L, R>>.mapLeft(transform: (L) -> X): Iterable<Both<X, R>> =
  map { it.mapLeft(transform) }

/**
 * Converts an instance [Pair] to an instance of [Both] with the same values
 */
fun <L, R> Pair<L, R>.asBoth() = Both(first, second)

/**
 * Returns a list containing the result of applying the given transform to the left value of each element in the
 * original collection and flattening it.
 *
 * @see [Both.flatMapLeft]
 */
fun <L, R, X> Iterable<Both<L, R>>.flatMapLeft(transform: (L) -> Both<X, *>): Iterable<Both<X, R>> =
  map { it.flatMapLeft(transform) }

/**
 * Returns a list containing the result of applying the given transform to the right value of each element in the
 * original collection.
 *
 * @see [Both.mapRight]
 */
fun <L, R, X> Iterable<Both<L, R>>.mapRight(transform: (R) -> X): Iterable<Both<L, X>> =
  map { it.mapRight(transform) }

/**
 * Returns a list containing the result of applying the given transform to the right value of each element in the
 * original collection and flattening it.
 *
 * @see [Both.flatMapRight]
 */
fun <L, R, X> Iterable<Both<L, R>>.flatMapRight(transform: (R) -> Both<*, X>): Iterable<Both<L, X>> =
  map { it.flatMapRight(transform) }

/**
 * Returns a sequence containing the result of applying the given transform to the left value of each element in the
 * original collection.
 *
 * @see [Both.mapLeft]
 */
fun <L, R, X> Sequence<Both<L, R>>.mapLeft(transform: (L) -> X): Sequence<Both<X, R>> =
  map { it.mapLeft(transform) }

/**
 * Returns a sequence containing the result of applying the given transform to the left value of each element in the
 * original collection and flattening it.
 *
 * @see [Both.flatMapLeft]
 */
fun <L, R, X> Sequence<Both<L, R>>.flatMapLeft(transform: (L) -> Both<X, *>): Sequence<Both<X, R>> =
  map { it.flatMapLeft(transform) }

/**
 * Returns a sequence containing the result of applying the given transform to the right value of each element in the
 * original collection.
 *
 * @see [Both.mapRight]
 */
fun <L, R, X> Sequence<Both<L, R>>.mapRight(transform: (R) -> X): Sequence<Both<L, X>> =
  map { it.mapRight(transform) }

/**
 * Returns a sequence containing the result of applying the given transform to the right value of each element in the
 * original collection and flattening it.
 *
 * @see [Both.flatMapRight]
 */
fun <L, R, X> Sequence<Both<L, R>>.flatMapRight(transform: (R) -> Both<*, X>): Sequence<Both<L, X>> =
  map { it.flatMapRight(transform) }

/**
 * Returns a new read-only map with the specified contents, given as a list of instances of [Both]
 * where the left value is the key and the right value is the value.
 */
fun <L, R> mapOf(vararg boths: Both<L, R>): Map<L, R> = mapOf(
  *boths.map { it.asPair() }.toTypedArray()
)

/**
 * Returns a new mutable map with the specified contents, given as a list of instances of [Both]
 * where the left value is the key and the right value is the value.
 */
fun <L, R> mutableMapOf(vararg boths: Both<L, R>): MutableMap<L, R> = mutableMapOf(
  *boths.map { it.asPair() }.toTypedArray()
)

/**
 * All entries of the map as a [Collection] of elements of type [Both].
 */
val <L, R> Map<L, R>.boths: Collection<Both<L, R>>
  get() = this.entries.map { Both(it.key, it.value) }
