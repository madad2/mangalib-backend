package dev.madad.mangalibbackend.dto

import dev.madad.mangalibbackend.entity.Chapter
import dev.madad.mangalibbackend.entity.Manga
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL

// Простой DTO, который ожидает ChapterController

data class ChapterDto(
    val id: Long = 0,
    @field:NotNull(message = "Номер главы не может быть null")
    @field:Min(value = 1, message = "Номер главы должен быть больше 0")
    val chapterNum: Int,
    @field:NotBlank(message = "Название главы не может быть пустым")
    @field:Size(max = 255, message = "Название главы не может превышать 255 символов")
    val title: String,
    @field:Size(max = 100, message = "Слишком много изображений (максимум 100)")
    val pagesImgUrls: List<
            @URL(message = "Некорректный URL изображения (http/https)")
            @Size(max = 500, message = "URL изображения слишком длинный (максимум 500 символов)")
            String> = emptyList()
)

// DTO для ответов (для вложений в мангу и списков)

data class ChapterResponseDto(
    val id: Long,
    val chapterNum: Int,
    val title: String,
    val pagesImgUrls: List<String>
)

// DTO для создания

data class ChapterCreateDto(
    @field:NotNull(message = "Номер главы не может быть null")
    @field:Min(value = 1, message = "Номер главы должен быть больше 0")
    val chapterNum: Int,

    @field:NotBlank(message = "Название главы не может быть пустым")
    @field:Size(max = 255, message = "Название главы не может превышать 255 символов")
    val title: String,

    @field:Size(max = 100, message = "Слишком много изображений (максимум 100)")
    val pagesImgUrls: List<
            @URL(message = "Некорректный URL изображения (http/https)")
            @Size(max = 500, message = "URL изображения слишком длинный (максимум 500 символов)")
            String> = emptyList(),

    @field:NotNull(message = "ID манги не может быть null")
    @field:Min(value = 1, message = "ID манги должен быть больше 0")
    val mangaId: Long
)

// DTO для обновления

data class ChapterUpdateDto(
    @field:NotNull(message = "Номер главы не может быть null")
    @field:Min(value = 1, message = "Номер главы должен быть больше 0")
    val chapterNum: Int,

    @field:NotBlank(message = "Название главы не может быть пустым")
    @field:Size(max = 255, message = "Название главы не может превышать 255 символов")
    val title: String,

    @field:Size(max = 100, message = "Слишком много изображений (максимум 100)")
    val pagesImgUrls: List<
            @URL(message = "Некорректный URL изображения (http/https)")
            @Size(max = 500, message = "URL изображения слишком длинный (максимум 500 символов)")
            String> = emptyList()
)

// Мапперы

fun Chapter.toDto() = ChapterDto(
    id = this.id,
    chapterNum = this.chapterNum,
    title = this.title,
    pagesImgUrls = this.pagesImgUrls
)

fun ChapterDto.toEntity(manga: Manga) = Chapter(
    id = this.id,
    chapterNum = this.chapterNum,
    title = this.title,
    pagesImgUrls = this.pagesImgUrls,
    manga = manga
)

fun Chapter.toResponseDto() = ChapterResponseDto(
    id = this.id,
    chapterNum = this.chapterNum,
    title = this.title,
    pagesImgUrls = this.pagesImgUrls
)

fun ChapterCreateDto.toEntity(manga: Manga) = Chapter(
    chapterNum = this.chapterNum,
    title = this.title,
    pagesImgUrls = this.pagesImgUrls,
    manga = manga
)

fun ChapterUpdateDto.applyTo(existing: Chapter): Chapter =
    existing.copy(
        chapterNum = this.chapterNum,
        title = this.title,
        pagesImgUrls = this.pagesImgUrls
    )


