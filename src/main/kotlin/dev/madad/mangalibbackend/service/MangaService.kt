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

    fun getMangaById(id: Long): Manga? = mangaRepository.findById(id).orElse(null)

    fun create(manga: Manga): Manga = mangaRepository.save(manga)

    fun update(id: Long, updatedManga: Manga): Manga? =
        mangaRepository.findById(id).map { existingManga ->
            existingManga.copy(
                title = updatedManga.title,
                description = updatedManga.description,
                coverImgUrl = updatedManga.coverImgUrl,
                chapters = updatedManga.chapters
            )
        }.map(mangaRepository::save).orElse(null)

    fun delete(id: Long) = mangaRepository.deleteById(id)
}