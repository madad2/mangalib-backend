package dev.madad.mangalibbackend.repository

import dev.madad.mangalibbackend.entity.Manga
import org.springframework.data.jpa.repository.JpaRepository

interface MangaRepository : JpaRepository<Manga, Long> {
}