package dev.madad.mangalibbackend.exception.manga

class MangaIllegalArgumentException(
    private val title: String
) : RuntimeException(
    "Манга с названием \"$title\" уже существует"
) {
}