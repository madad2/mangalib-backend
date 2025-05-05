package dev.madad.mangalibbackend.controller

import dev.madad.mangalibbackend.entity.Manga
import dev.madad.mangalibbackend.service.MangaService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST-контроллер для работы с мангой.
 */
@RestController
@RequestMapping("/api/manga")
class MangaController(
    private val mangaService: MangaService
) {
    @GetMapping
    fun getAllManga() = mangaService.getAllManga()

    @GetMapping("/{id}")
    fun getMangaById(@PathVariable id: Long): Manga? = mangaService.getMangaById(id)

    @PostMapping
    fun createManga(@RequestBody manga: Manga): Manga = mangaService.create(manga)

    @PutMapping("/{id}")
    fun updateManga(@PathVariable id: Long, @RequestBody manga: Manga): Manga? = mangaService.update(id, manga)

    @DeleteMapping("/{id}")
    fun deleteManga(@PathVariable id: Long) = mangaService.delete(id)
}