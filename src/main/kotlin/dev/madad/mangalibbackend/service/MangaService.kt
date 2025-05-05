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
    fun getAllManga() = mangaRepository.findAll()

    fun getMangaById(id: Long) = mangaRepository.findById(id).orElse(null)

    fun create(manga: Manga) = mangaRepository.save(manga)

    fun update(id: Long, updatedManga: Manga): Manga? {
        return mangaRepository.findById(id).map { existingManga ->
            val mangaToUpdate = existingManga.copy(
                title = updatedManga.title,
                description = updatedManga.description,
                coverImgUrl = updatedManga.coverImgUrl,
                chapters = updatedManga.chapters
            )
            mangaRepository.save(mangaToUpdate)
        }.orElse(null)
    }

    fun delete(id: Long) = mangaRepository.deleteById(id)
}