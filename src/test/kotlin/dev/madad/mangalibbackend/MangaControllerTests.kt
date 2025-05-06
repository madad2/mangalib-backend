package dev.madad.mangalibbackend

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.madad.mangalibbackend.controller.MangaController
import dev.madad.mangalibbackend.entity.Manga
import dev.madad.mangalibbackend.service.MangaService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(MangaController::class)
class MangaControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var mangaService: MangaService
    
    @Test
    fun `test get all manga`() {
        // Тестовые данные
        val mangaList = listOf(
            Manga(id = 1, title = "One Piece", description = "Эпическое приключение", coverImgUrl = "url1", chapters = emptyList()),
            Manga(id = 2, title = "Naruto", description = "Приключения ниндзя", coverImgUrl = "url2", chapters = emptyList())
        )

        // Настройка мока для сервиса
        Mockito.`when`(mangaService.getAllManga()).thenReturn(mangaList)

        val mapper = jacksonObjectMapper()
        val expectedJson = mapper.writeValueAsString(mangaList)

        // Тестирование эндпойнта GET /api/manga
        mockMvc.perform(MockMvcRequestBuilders.get("/api/manga"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(expectedJson))

    }
}