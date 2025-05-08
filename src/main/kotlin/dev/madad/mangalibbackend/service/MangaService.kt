package dev.madad.mangalibbackend.service

import dev.madad.mangalibbackend.entity.Manga
import dev.madad.mangalibbackend.repository.MangaRepository
import org.springframework.stereotype.Service

/**
 * Сервис для работы с мангой.
 */
@Service
class MangaService(
    private val mangaRepository: MangaRepository
) {
    fun getAllManga(): List<Manga> = mangaRepository.findAll()

    fun getMangaById(id: Long): Manga = mangaRepository.findById(id).orElse(null)
        ?: throw NoSuchElementException("Манга с id=$id не найдена")

    fun getMangaByTitle(title: String): Manga = mangaRepository.findByTitle(title)
        ?: throw NoSuchElementException("Манга с названием '$title' не найдена")

    fun create(manga: Manga): Manga = mangaRepository.save(manga)

    fun update(id: Long, updatedManga: Manga): Manga {
        val existing = getMangaById(id)
        val toSave = existing.copy(
            title = updatedManga.title,
            description = updatedManga.description,
            coverImgUrl = updatedManga.coverImgUrl,
            chapters = updatedManga.chapters
        )
        return mangaRepository.save(toSave)
    }

    fun delete(id: Long) {
        if (!mangaRepository.existsById(id)) {
            throw NoSuchElementException("Манга с id=$id не найдена")
        }
        mangaRepository.deleteById(id)
    }

    fun delete(title: String) {
        if (!mangaRepository.existsByTitle(title)) {
            throw NoSuchElementException("Манга с названием '$title' не найдена")
        }
        mangaRepository.deleteByTitle(title)
    }
}