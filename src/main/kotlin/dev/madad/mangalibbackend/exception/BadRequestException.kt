package dev.madad.mangalibbackend.exception

/**
 * Exception thrown when a request contains invalid data or violates business rules.
 */
class BadRequestException(message: String) : RuntimeException(message)