package dev.madad.mangalibbackend.controller

import dev.madad.mangalibbackend.entity.Manga
import dev.madad.mangalibbackend.service.MangaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun getAllManga(): ResponseEntity<List<Manga>> = ResponseEntity.ok(mangaService.getAllManga())

    @GetMapping("/{id}")
    fun getMangaById(@PathVariable id: Long): ResponseEntity<Manga> =
        mangaService.getMangaById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping
    fun createManga(@RequestBody manga: Manga): ResponseEntity<Manga> =
        ResponseEntity.status(HttpStatus.CREATED).body(mangaService.create(manga))

    @PutMapping("/{id}")
    fun updateManga(@PathVariable id: Long, @RequestBody manga: Manga): ResponseEntity<Manga> =
        mangaService.update(id, manga)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{id}")
    fun deleteManga(@PathVariable id: Long): ResponseEntity<Unit> {
        mangaService.delete(id)
        return ResponseEntity.noContent().build()
    }
}