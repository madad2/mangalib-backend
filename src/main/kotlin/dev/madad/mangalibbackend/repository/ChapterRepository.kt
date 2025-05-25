package dev.madad.mangalibbackend.repository

import dev.madad.mangalibbackend.entity.Chapter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChapterRepository : JpaRepository<Chapter, Long> {
    fun getChaptersByMangaId(mangaId: Long): List<Chapter>
    fun getChaptersByMangaTitle(title: String): List<Chapter>
    fun existsByChapterNumAndMangaId(chapterNum: Int, mangaId: Long): Boolean
    fun existsByChapterNumAndMangaTitle(chapterNum: Int, title: String): Boolean
}