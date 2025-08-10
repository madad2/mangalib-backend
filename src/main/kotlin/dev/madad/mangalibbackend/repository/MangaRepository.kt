package dev.madad.mangalibbackend.repository

import dev.madad.mangalibbackend.entity.Manga
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MangaRepository : JpaRepository<Manga, Long> {
    fun findByTitle(title: String): Manga?
    fun existsByTitle(title: String): Boolean
    fun deleteByTitle(title: String)
    fun findByTitleContainingIgnoreCase(partTitle: String): List<Manga>
}