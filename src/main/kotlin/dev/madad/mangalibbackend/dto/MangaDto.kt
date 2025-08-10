package dev.madad.mangalibbackend.dto

import dev.madad.mangalibbackend.entity.Manga
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL

// Простой DTO, который ожидает MangaController

data class MangaDto(
    val id: Long = 0,
    @field:NotBlank(message = "Название манги не может быть пустым")
    @field:Size(max = 255, message = "Название должно быть не длиннее 255 символов")
    val title: String,
    @field:NotBlank(message = "Описание манги не может быть пустым")
    val description: String,
    @field:URL(message = "URL обложки должен быть корректным (http/https)")
    val coverImgUrl: String
)

// Короткий DTO для списков

data class MangaListDto(
    val id: Long,
    val title: String,
    val coverImgUrl: String
)

// Детальный DTO

data class MangaDetailsDto(
    val id: Long,
    val title: String,
    val description: String,
    val coverImgUrl: String,
    val chapters: List<ChapterResponseDto> = emptyList()
)

// DTO для создания/обновления

data class MangaUpsertDto(
    @field:NotBlank(message = "Название манги не может быть пустым")
    @field:Size(max = 255, message = "Название должно быть не длиннее 255 символов")
    val title: String,

    @field:NotBlank(message = "Описание манги не может быть пустым")
    val description: String,

    @field:URL(message = "URL обложки должен быть корректным (http/https)")
    val coverImgUrl: String
)

// Мапперы

fun Manga.toDto() = MangaDto(
    id = this.id,
    title = this.title,
    description = this.description,
    coverImgUrl = this.coverImgUrl
)

fun MangaDto.toEntity() = Manga(
    id = this.id,
    title = this.title,
    description = this.description,
    coverImgUrl = this.coverImgUrl
)

fun Manga.toListDto() = MangaListDto(
    id = this.id,
    title = this.title,
    coverImgUrl = this.coverImgUrl
)

fun Manga.toDetailsDto() = MangaDetailsDto(
    id = this.id,
    title = this.title,
    description = this.description,
    coverImgUrl = this.coverImgUrl,
    chapters = this.chapters.map { it.toResponseDto() }
)

fun MangaUpsertDto.toEntity(id: Long) = Manga(
    id = id,
    title = this.title,
    description = this.description,
    coverImgUrl = this.coverImgUrl
)
