package dev.madad.mangalibbackend.dto

import dev.madad.mangalibbackend.entity.Manga
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class MangaDto(
    val id: Long? = null,

    @NotBlank(message = "Название манги не может быть пустым")
    @Size(max = 255, message = "Название должно быть не длиннее 255 символов")
    val title: String,

    @NotBlank(message = "Описание манги не может быть пустым")
    val description: String,

    @NotBlank(message = "URL обложки не может быть пустым")
    val coverImgUrl: String
)

fun Manga.toDto() = MangaDto(
    id = this.id,
    title = this.title,
    description = this.description,
    coverImgUrl = this.coverImgUrl
)

fun MangaDto.toEntity() = Manga(
    id = this.id ?: 0,
    title = this.title,
    description = this.description,
    coverImgUrl = this.coverImgUrl,
    chapters = emptyList()
)
