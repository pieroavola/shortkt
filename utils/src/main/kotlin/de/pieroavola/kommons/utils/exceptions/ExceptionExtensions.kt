package de.pieroavola.kommons.utils.exceptions

/**
 * Returns the simple class name and message of the exception, leaving out the stack trace.
 */
val Throwable.description: String
  get() = "${javaClass.simpleName}: $message"
