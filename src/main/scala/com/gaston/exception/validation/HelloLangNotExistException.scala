package com.gaston.exception.validation

case class HelloLangNotExistException(message: String, cause: Option[Throwable])
    extends ValidationException
