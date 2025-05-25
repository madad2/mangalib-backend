package dev.madad.mangalibbackend.controller

import dev.madad.mangalibbackend.dto.MangaDto
import dev.madad.mangalibbackend.dto.toDto
import dev.madad.mangalibbackend.dto.toEntity
import dev.madad.mangalibbackend.service.MangaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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
    fun getAllManga(): ResponseEntity<List<MangaDto>> = ResponseEntity.ok(mangaService.getAllManga().map { it.toDto() })

    @GetMapping("/{id}")
    fun getMangaById(@PathVariable id: Long): ResponseEntity<MangaDto> =
        mangaService.getMangaById(id).let { ResponseEntity.ok(it.toDto()) }

    @PostMapping
    fun createManga(@Valid @RequestBody mangaDto: MangaDto): ResponseEntity<MangaDto> {
        val createdEntity = mangaService.createManga(mangaDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity.toDto())
    }

    @PutMapping("/{id}")
    fun updateManga(@PathVariable id: Long, @Valid @RequestBody mangaDto: MangaDto): ResponseEntity<MangaDto> =
        ResponseEntity.ok(mangaService.updateManga(id, mangaDto.toEntity()).toDto())

    @DeleteMapping("/{id}")
    fun deleteManga(@PathVariable id: Long): ResponseEntity<Unit> {
        mangaService.deleteManga(id)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteMangaByTitle(@RequestParam title: String): ResponseEntity<Unit> {
        mangaService.deleteManga(title)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    fun searchManga(@RequestParam fragment: String): ResponseEntity<List<MangaDto>> {
        val results = mangaService.searchByTitleFragment(fragment)
        val dtoList = results.map { it.toDto() }
        return ResponseEntity.ok(dtoList)
    }
}