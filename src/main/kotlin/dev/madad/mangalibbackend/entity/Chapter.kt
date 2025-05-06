package dev.madad.mangalibbackend.entity

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

/**
 * JPA-сущность, представляющая главу манги.
 *
 * @property id Уникальный идентификатор главы.
 * @property chapterNum Номер или порядок главы.
 * @property title Название или заголовок главы.
 * @property summary Краткое содержание или описание главы.
 * @property pageImgUrls Список URL изображений страниц главы.
 * @property manga Манга, к которой принадлежит данная глава.
 */
@Entity
@Table(name = "chapter")
data class Chapter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val chapterNum: Int = 0,

    @Column(nullable = false)
    val title: String = "",

    @Column()
    val summary: String = "",

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "chapter_page", joinColumns = [JoinColumn(name = "chapter_id")])
    @Column(name = "page_img_urls")
    val pageImgUrls: List<String> = emptyList(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manga_id")
    val manga: Manga
)
