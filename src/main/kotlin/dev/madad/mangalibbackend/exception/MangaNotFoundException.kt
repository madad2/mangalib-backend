package dev.madad.mangalibbackend.exception.manga

class MangaNotFoundException : RuntimeException {
    constructor(id: Long) : super("Не удалось найти мангу с идентификатором: $id")
    constructor(title: String) : super("Не удалось найти мангу с названием: $title")
}