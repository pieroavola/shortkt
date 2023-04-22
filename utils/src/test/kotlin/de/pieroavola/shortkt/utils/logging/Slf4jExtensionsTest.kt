package de.pieroavola.shortkt.utils.logging

import de.pieroavola.shortkt.test.data.strings
import de.pieroavola.shortkt.utils.exceptions.description
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.slf4j.Logger

class Slf4jExtensionsTest {

  @Test
  internal fun providesCorrectLoggerForClass() {
    assertThat(logger().name).isEqualTo(this.javaClass.canonicalName)
  }

  @Test
  internal fun providesLoggerWithName() {
    val (name) = strings()
    assertThat(logger(name).name).isEqualTo(name)
  }

  @Test
  internal fun logsMessageOnErrorLevel() {

    val loggerMock = getLoggerMock()
    val (message) = strings()

    loggerMock.exception(message, RuntimeException())

    verify(loggerMock).error(message)
  }

  @Test
  internal fun logsExceptionDescriptionOnErrorLevel() {

    val loggerMock = getLoggerMock()
    val (message, exceptionMessage) = strings()
    val exception = RuntimeException(exceptionMessage)

    loggerMock.exception(message, exception)

    verify(loggerMock).error(exception.description)
  }

  @Test
  internal fun onlyLogsExceptionIfMessageNull() {

    val loggerMock = getLoggerMock()

    loggerMock.exception(null, RuntimeException(""))

    verify(loggerMock, times(1)).error(anyOrNull())
  }

  @Test
  internal fun logsStacktraceOnDebugLevelByDefault() {

    val loggerMock = getLoggerMock()
    val (message, exceptionMessage) = strings()
    val exception = RuntimeException(exceptionMessage)

    loggerMock.exception(message, exception)

    verify(loggerMock).debug("", exception)
  }

  @Test
  internal fun doesNotLogStacktraceIfDisabled() {

    val loggerMock = getLoggerMock()

    loggerMock.exception(null, RuntimeException(), false)

    verify(loggerMock, never()).debug(anyOrNull<String>(), anyOrNull<Throwable>())
  }

  private fun getLoggerMock() = mock<Logger>()
}
