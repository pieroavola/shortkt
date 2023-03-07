package de.pieroavola.kommons.utils.logging

import de.pieroavola.kommons.utils.exceptions.description
import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)
fun logger(name: String): Logger = LoggerFactory.getLogger(name)

fun Logger.exception(message: String?, throwable: Throwable, logStacktrace: Boolean = true) {

  message?.run { error(this) }
  error(throwable.description)

  if (logStacktrace) {
    debug("", throwable)
  }
}
