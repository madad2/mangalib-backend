package dev.madad.mangalibbackend.repository

import dev.madad.mangalibbackend.entity.Chapter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChapterRepository : JpaRepository<Chapter, Long> {
    fun findByMangaTitle(title: String): List<Chapter>
    fun existsByChapterNumAndMangaTitle(chapterNum: Int, title: String): Boolean
}