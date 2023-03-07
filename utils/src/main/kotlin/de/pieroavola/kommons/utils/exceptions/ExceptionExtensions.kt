package de.pieroavola.kommons.utils.exceptions

val Throwable.description: String
  get() = "${javaClass.simpleName}: $message"
