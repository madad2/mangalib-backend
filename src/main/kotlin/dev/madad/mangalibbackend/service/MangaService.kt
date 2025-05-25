package dev.madad.mangalibbackend.service

import dev.madad.mangalibbackend.entity.Manga
import dev.madad.mangalibbackend.exception.NotFoundException
import dev.madad.mangalibbackend.repository.MangaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Сервис для работы с мангой.
 */
@Service
class MangaService(
    private val mangaRepository: MangaRepository
) {

    // Получить список манг
    @Transactional(readOnly = true)
    fun getAllManga(): List<Manga> = mangaRepository.findAll()

    // Получить мангу по id
    @Transactional(readOnly = true)
    fun getMangaById(id: Long): Manga = mangaRepository.findById(id).orElse(null)
        ?: throw NotFoundException("Манга с id=$id не найдена")

    // Получить мангу по названию
    @Transactional(readOnly = true)
    fun getMangaByTitle(title: String): Manga = mangaRepository.findByTitle(title)
        ?: throw NotFoundException("Манга с названием '$title' не найдена")

    // Создать мангу
    @Transactional
    fun createManga(manga: Manga): Manga {
        if (mangaRepository.existsByTitle(manga.title)) throw IllegalArgumentException("Манга с названием '${manga.title}' уже существует")
        return mangaRepository.save(manga)
    }

    // Обновить данные манги
    @Transactional
    fun updateManga(id: Long, updatedManga: Manga): Manga {
        val existingManga = getMangaById(id)
        val updatedManga = existingManga.copy(
            title = updatedManga.title,
            description = updatedManga.description,
            coverImgUrl = updatedManga.coverImgUrl,
            chapters = updatedManga.chapters
        )
        return mangaRepository.save(updatedManga)
    }

    // Удалить мангу по id
    @Transactional
    fun deleteManga(id: Long) {
        if (!mangaRepository.existsById(id)) {
            throw NotFoundException("Манга с id=$id не найдена")
        }
        mangaRepository.deleteById(id)
    }

    // Удалить мангу по названию
    @Transactional
    fun deleteManga(title: String) {
        if (!mangaRepository.existsByTitle(title)) {
            throw NotFoundException("Манга с названием '$title' не найдена")
        }
        mangaRepository.deleteByTitle(title)
    }
}