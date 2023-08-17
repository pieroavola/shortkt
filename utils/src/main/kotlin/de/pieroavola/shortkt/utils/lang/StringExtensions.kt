package de.pieroavola.shortkt.utils.lang

import java.util.Base64

/**
 * Encodes the [ByteArray] to a [String] using Base64.
 *
 * @return The Base64 encoded bytes.
 */
fun ByteArray.toBase64String(): String = Base64.getEncoder().encodeToString(this)

/**
 * Decodes a Base64-encoded [String].
 *
 * @return The [ByteArray] that was Base64-encoded.
 */
fun String.decodeBase64(): ByteArray = Base64.getDecoder().decode(this)

/**
 * Converts a [ByteArray] to a [String] by joining the hex representation of each [Byte].
 *
 * @return The hex-string.
 */
fun ByteArray.toHexString(): String = joinToString(separator = "") { "%02x".format(it) }

/**
 * Converts a hexadecimal [String] to a [ByteArray] by splitting into chunks of two characters and interpreting those
 * as a byte.
 *
 * @return The hex-encoded byte array.
 *
 * @throws NumberFormatException If the string is not hexadecimal.
 * @throws IllegalArgumentException If the string is not of even length
 */
@Throws(NumberFormatException::class)
fun String.fromHexString(): ByteArray = this
  .takeIf { it.length % 2 == 0 }
  ?.chunked(2)
  ?.map { it.toInt(16).toByte() }
  ?.toByteArray()
  ?: throw IllegalArgumentException("A hex string must be an even number of characters")
