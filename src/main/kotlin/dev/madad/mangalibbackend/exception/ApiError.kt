package dev.madad.mangalibbackend.exception

data class ApiError(
    val status: Int,
    val message: String,
    val errors: List<String> = emptyList()
)
