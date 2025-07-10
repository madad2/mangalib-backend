package dev.madad.mangalibbackend.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.method.MethodValidationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException): ResponseEntity<ApiError> = ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(
            ApiError(
                status = HttpStatus.NOT_FOUND.value(),
                message = ex.message ?: "Не найдено",
            )
        )

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(ex: BadRequestException): ResponseEntity<ApiError> = ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(
            ApiError(
                status = HttpStatus.BAD_REQUEST.value(),
                message = ex.message ?: "Некорректный запрос",
            )
        )

    @ExceptionHandler(MethodValidationException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<ApiError> {
        val errors = ex.bindingResult
            .allErrors
            .map { err ->
                when (err) {
                    is FieldError -> "${err.field}: ${err.defaultMessage}"
                    else -> err.defaultMessage ?: "Validation error"
                }
            }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ApiError(
                    status = HttpStatus.BAD_REQUEST.value(),
                    message = "Ошибка валидации",
                    errors = errors
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ApiError> = ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            ApiError(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = ex.message ?: "Internal server error"
            )
        )
}
