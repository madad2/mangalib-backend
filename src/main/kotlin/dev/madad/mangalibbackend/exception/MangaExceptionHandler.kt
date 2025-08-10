package dev.madad.mangalibbackend.exception.manga

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class MangaExceptionHandler {

    @ExceptionHandler(MangaNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onMangaNotFound(e: MangaNotFoundException) = mapOf(
        "errorCode" to "MANGA_NOT_FOUND",
        "message" to e.message
    )

    fun onMangaIllegalArgument(e: MangaIllegalArgumentException) = mapOf(
        "errorCode" to "MANGA_ILLEGAL_ARGUMENT",
        "message" to e.message
    )
}
