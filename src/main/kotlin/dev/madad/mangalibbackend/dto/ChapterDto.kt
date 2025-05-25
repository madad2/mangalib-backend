package dev.madad.mangalibbackend.dto

import dev.madad.mangalibbackend.entity.Chapter
import dev.madad.mangalibbackend.entity.Manga
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class ChapterDto(
    val id: Long? = null,

    @NotNull(message = "Номер главы не может быть null")
    @Min(value = 1, message = "Номер главы должен быть больше 0")
    val chapterNum: Int,

    @NotBlank(message = "Название главы не может быть пустым")
    @Size(max = 255, message = "Название главы не может превышать 255 символов")
    val title: String,

    @Size(max = 100, message = "Слишком много изображений (максимум 100)")
    val pageImgUrls: List<@Pattern(
        regexp = "^(http|https)://.*$",
        message = "Некорректный URL изображения"
    ) String> = emptyList(),

    @NotNull(message = "ID манги не может быть null")
    @Min(value = 1, message = "ID манги должен быть больше 0")
    val mangaId: Long
)


fun Chapter.toDto(): ChapterDto = ChapterDto(
    id = this.id,
    chapterNum = this.chapterNum,
    title = this.title,
    pageImgUrls = this.pageImgUrls,
    mangaId = this.manga.id
)

fun ChapterDto.toEntity(manga: Manga): Chapter = Chapter(
    id = this.id ?: 0,
    chapterNum = this.chapterNum,
    title = this.title,
    pageImgUrls = this.pageImgUrls,
    manga = manga
)

