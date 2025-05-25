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
    @Transactional
    fun getChaptersByMangaId(mangaId: Long): List<Chapter> {
        val manga = mangaRepository.findById(mangaId)
            .orElseThrow { IllegalArgumentException("Манга с ID $mangaId не найдена") }
        return chapterRepository.getChaptersByMangaId(mangaId)
    }

    // Получить все главы манги
    @Transactional(readOnly = true)
    fun getChaptersByMangaTitle(title: String): List<Chapter> {
        val manga =
            mangaRepository.findByTitle(title) ?: throw IllegalArgumentException("Манга с названием $title не найдена")
        return chapterRepository.getChaptersByMangaTitle(manga.title)
    }

    // Добавить главу в мангу
    @Transactional
    fun addChapter(chapter: Chapter, mangaId: Long): Chapter {
        val manga = mangaRepository.findById(mangaId)
            .orElseThrow { IllegalArgumentException("Манга с ID $mangaId не найдена") }

        if (chapterRepository.existsByChapterNumAndMangaId(chapter.chapterNum, mangaId)) {
            throw IllegalArgumentException("Глава с номером ${chapter.chapterNum} уже существует для манги с ID $mangaId")
        }

        return chapterRepository.save(chapter.copy(manga = manga))
    }

    // Обновить главу по ID
    @Transactional
    fun updateChapter(mangaId: Long, chapterId: Long, updatedChapter: Chapter): Chapter {
        // Проверяем, что манга существует
        val manga = mangaRepository.findById(mangaId)
            .orElseThrow { IllegalArgumentException("Манга с ID $mangaId не найдена") }

        // Проверяем, что глава существует
        val existingChapter = chapterRepository.findById(chapterId)
            .orElseThrow { IllegalArgumentException("Глава с ID $chapterId не найдена") }

        // Убеждаемся, что глава принадлежит указанной манге
        if (existingChapter.manga?.id != manga.id) {
            throw IllegalArgumentException("Глава с ID $chapterId не принадлежит манге с ID $mangaId")
        }

        // Обновляем данные главы
        val chapterToSave = existingChapter.copy(
            chapterNum = updatedChapter.chapterNum,
            title = updatedChapter.title,
            pageImgUrls = updatedChapter.pageImgUrls
        )

        return chapterRepository.save(chapterToSave)
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