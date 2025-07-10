package dev.madad.mangalibbackend.service

import dev.madad.mangalibbackend.entity.Chapter
import dev.madad.mangalibbackend.exception.BadRequestException
import dev.madad.mangalibbackend.exception.NotFoundException
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

    // Получить главу по ID и ID манги
    @Transactional(readOnly = true)
    fun getChapterByIdAndMangaId(id: Long, mangaId: Long): Chapter {
        return chapterRepository.findByIdAndMangaId(id, mangaId)
            ?: throw NotFoundException("Глава с ID $id не найдена для манги с ID $mangaId")
    }

    // Получить все главы манги
    @Transactional(readOnly = true)
    fun getChaptersByMangaId(mangaId: Long): List<Chapter> {
        val manga = mangaRepository.findById(mangaId)
            .orElseThrow { NotFoundException("Манга с ID $mangaId не найдена") }
        return chapterRepository.getChaptersByMangaId(mangaId)
    }

    // Получить все главы манги
    @Transactional(readOnly = true)
    fun getChaptersByMangaTitle(title: String): List<Chapter> {
        val manga =
            mangaRepository.findByTitle(title) ?: throw NotFoundException("Манга с названием $title не найдена")
        return chapterRepository.getChaptersByMangaTitle(manga.title)
    }

    // Добавить главу в мангу
    @Transactional
    fun addChapter(chapter: Chapter, mangaId: Long): Chapter {
        val manga = mangaRepository.findById(mangaId)
            .orElseThrow { NotFoundException("Манга с ID $mangaId не найдена") }

        if (chapterRepository.existsByChapterNumAndMangaId(chapter.chapterNum, mangaId)) {
            throw BadRequestException("Глава с номером ${chapter.chapterNum} уже существует для манги с ID $mangaId")
        }

        return chapterRepository.save(chapter.copy(manga = manga))
    }

    // Обновить главу по ID
    @Transactional
    fun updateChapter(mangaId: Long, chapterId: Long, updatedChapter: Chapter): Chapter {
        // Проверяем, что манга существует
        val manga = mangaRepository.findById(mangaId)
            .orElseThrow { NotFoundException("Манга с ID $mangaId не найдена") }

        // Проверяем, что глава существует
        val existingChapter = chapterRepository.findById(chapterId)
            .orElseThrow { NotFoundException("Глава с ID $chapterId не найдена") }

        // Убеждаемся, что глава принадлежит указанной манге
        if (existingChapter.manga?.id != manga.id) {
            throw BadRequestException("Глава с ID $chapterId не принадлежит манге с ID $mangaId")
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
            throw NotFoundException("Глава с ID $id не найдена")
        }
        chapterRepository.deleteById(id)
    }
}
