package de.pieroavola.kommons.utils.exceptions

import de.pieroavola.kommons.test.data.strings
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.IOException

class ExceptionExtensionsTest {

  @Test
  internal fun returnsCorrectDescription() {

    val (message) = strings()
    val exception = IOException(message)

    assertThat(exception.description).isEqualTo("IOException: $message")
  }
}
