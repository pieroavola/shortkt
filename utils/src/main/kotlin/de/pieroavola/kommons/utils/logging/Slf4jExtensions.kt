package de.pieroavola.kommons.utils.logging

import de.pieroavola.kommons.utils.exceptions.description
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Get the SLF4J logger of the current class.
 *
 * @return The logger for the class in which the method is called.
 */
inline fun <reified T> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)

/**
 * Get the SLF4J logger with a specific name.
 *
 * @param name The name of the logger.
 * @return The logger with the specified name.
 */
fun logger(name: String): Logger = LoggerFactory.getLogger(name)

/**
 * Extension to log exception information on multiple log levels.
 *
 * Optionally logs a message on ERROR level.
 * Then logs the exception's description on ERROR level.
 * Then optionally logs the stack trace on DEBUG level.
 *
 * @receiver [org.slf4j.Logger]
 *
 * @see [kotlin.Throwable.description]
 *
 * @param message Log message, nothing is logged when parameter is
 * @param throwable The exception
 * @param logStacktrace Stacktrace logging flag
 */
fun Logger.exception(message: String?, throwable: Throwable, logStacktrace: Boolean = true) {

  message?.run { error(this) }
  error(throwable.description)

  if (logStacktrace) {
    debug("", throwable)
  }
}
