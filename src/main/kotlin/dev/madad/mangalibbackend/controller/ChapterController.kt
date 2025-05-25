package dev.madad.mangalibbackend.controller

import dev.madad.mangalibbackend.dto.ChapterDto
import dev.madad.mangalibbackend.dto.toDto
import dev.madad.mangalibbackend.dto.toEntity
import dev.madad.mangalibbackend.service.ChapterService
import dev.madad.mangalibbackend.service.MangaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST-контроллер для работы с главами манги.
 */
@RestController
@RequestMapping("/api/manga/{mangaId}/chapters")
class ChapterController(
    private val chapterService: ChapterService,
    private val mangaService: MangaService
) {

    // Получить все главы манги
    @GetMapping
    fun getChapters(@PathVariable mangaId: Long): ResponseEntity<List<ChapterDto>> {
        val chapters = chapterService.getChaptersByMangaId(mangaId)
        return ResponseEntity.ok(chapters.map { it.toDto() })
    }

    // Добавить новую главу в мангу
    @PostMapping
    fun addChapter(
        @PathVariable mangaId: Long,
        @Valid @RequestBody chapterDto: ChapterDto
    ): ResponseEntity<ChapterDto> {
        val manga = mangaService.getMangaById(mangaId)
        val chapter = chapterService.addChapter(chapterDto.toEntity(manga), mangaId)
        return ResponseEntity.status(HttpStatus.CREATED).body(chapter.toDto())
    }

    // Получить главу по ID
    @GetMapping("/{id}")
    fun getChapterById(
        @PathVariable mangaId: Long,
        @PathVariable id: Long
    ): ResponseEntity<ChapterDto> {
        val chapter = chapterService.getChaptersByMangaId(mangaId).find { it.id == id }
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(chapter.toDto())
    }

    // Обновить данные главы
    @PostMapping("/{id}")
    fun updateChapter(
        @PathVariable mangaId: Long,
        @PathVariable id: Long,
        @Valid @RequestBody chapterDto: ChapterDto
    ): ResponseEntity<ChapterDto> {
        val manga = mangaService.getMangaById(mangaId)
        val updatedChapter = chapterService.updateChapter(mangaId, id, chapterDto.toEntity(manga.copy(id = mangaId)))
        return ResponseEntity.ok(updatedChapter.toDto())
    }

    // Удалить главу
    @DeleteMapping("/{id}")
    fun deleteChapter(
        @PathVariable mangaId: Long,
        @PathVariable id: Long
    ): ResponseEntity<Unit> {
        chapterService.deleteChapter(id)
        return ResponseEntity.noContent().build()
    }
}
