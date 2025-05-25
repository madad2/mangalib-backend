package dev.madad.mangalibbackend.service

import dev.madad.mangalibbackend.entity.Chapter
import dev.madad.mangalibbackend.repository.ChapterRepository
import dev.madad.mangalibbackend.repository.MangaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Сервис для работы с главами манги.
 */
@Service
class ChapterService(
    private val chapterRepository: ChapterRepository,
    private val mangaRepository: MangaRepository
) {

    // Получить все главы манги
    @Transactional(readOnly = true)
    fun getChapterByMangaTitle(title: String): List<Chapter> {
        val manga =
            mangaRepository.findByTitle(title) ?: throw IllegalArgumentException("Манга с названием $title не найдена")
        return chapterRepository.findByMangaTitle(manga.title)
    }

    // Добавить главу в мангу
    @Transactional
    fun addChapter(chapter: Chapter, mangaTitle: String): Chapter {
        val manga = mangaRepository.findByTitle(mangaTitle)
            ?: throw IllegalArgumentException("Манга с названием '$mangaTitle' не найдена")

        if (chapterRepository.existsByChapterNumAndMangaTitle(chapter.chapterNum, manga.title)) {
            throw IllegalArgumentException("Глава с номером ${chapter.chapterNum} уже существует для манги '${manga.title}'")
        }

        val chapterWithManga = chapter.copy(manga = manga)
        return chapterRepository.save(chapterWithManga)
    }

    // Удалить главу по ID
    @Transactional
    fun deleteChapter(id: Long) {
        if (!chapterRepository.existsById(id)) {
            throw IllegalArgumentException("Глава с ID $id не найдена")
        }
        chapterRepository.deleteById(id)
    }
}