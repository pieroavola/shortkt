package de.pieroavola.shortkt.utils.lang

import de.pieroavola.shortkt.test.data.bytes
import de.pieroavola.shortkt.test.data.strings
import de.pieroavola.shortkt.test.data.toByteArray
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.Base64

class StringExtensionsTest {

  @Test
  internal fun encodesByteArrayToBase64() {

    val (originalString) = strings()

    val encoded = originalString.encodeToByteArray().toBase64String()
    val systemEncoded = String(cmd(listOf("base64"), originalString)).trim()

    assertThat(encoded).isEqualTo(systemEncoded)
  }

  @Test
  internal fun decodesBase64StringToByteArray() {

    val (base64String) = strings().map { Base64.getEncoder().encodeToString(it.encodeToByteArray()) }

    val decoded = base64String.decodeBase64()
    val systemDecoded = cmd(listOf("base64", "-d"), base64String)

    assertThat(decoded).isEqualTo(systemDecoded)
  }

  @Test
  internal fun convertsByteArrayToHexStringAndBack() {

    val bytes = bytes().toByteArray()

    assertThat(bytes.toHexString().fromHexString()).isEqualTo(bytes)
  }

  @Test
  internal fun correctlyConvertsByteArrayToHexString() {

    val byteArray = byteArrayOf(0x00, 0x2f, 0x44, 0x7f, 0x6a, -0x01)
    assertThat(byteArray.toHexString()).isEqualTo("002f447f6aff")
  }

  @Test
  internal fun throwsIfHexStringIsNotHex() {
    assertThatCode { "xy".fromHexString() }.isExactlyInstanceOf(NumberFormatException::class.java)
  }

  @Test
  internal fun throwsIfHexStringIsNotEvenInLength() {
    assertThatCode { "12345".fromHexString() }.isExactlyInstanceOf(IllegalArgumentException::class.java)
  }

  //<editor-fold desc="Helper">

  private fun cmd(command: List<String>, input: String): ByteArray {
    val processBuilder = ProcessBuilder(command)
    processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE)
    processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE)

    val process = processBuilder.start()

    val outputStream = process.outputStream
    outputStream.bufferedWriter().use { writer ->
      writer.write(input)
      writer.flush()
    }

    val inputStream = process.inputStream
    val outputBytes = inputStream.readBytes()

    process.waitFor()
    process.destroy()

    return outputBytes
  }

  //</editor-fold>
}
