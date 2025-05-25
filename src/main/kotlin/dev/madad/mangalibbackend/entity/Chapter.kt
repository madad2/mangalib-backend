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
import jakarta.persistence.UniqueConstraint

/**
 * JPA-сущность, представляющая главу манги.
 *
 * @property id Уникальный идентификатор главы.
 * @property chapterNum Номер или порядок главы.
 * @property title Название или заголовок главы.
 * @property pageImgUrls Список URL изображений страниц главы.
 * @property manga Манга, к которой принадлежит данная глава.
 */
@Entity
@Table(
    name = "chapter",
    uniqueConstraints = [UniqueConstraint(columnNames = ["manga_id", "chapter_num"])]
)
data class Chapter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val chapterNum: Int = 0,

    @Column(nullable = false, length = 255)
    val title: String = "",

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "chapter_page", joinColumns = [JoinColumn(name = "chapter_id")])
    @Column(name = "page_img_urls", length = 500)
    val pageImgUrls: List<String> = emptyList(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manga_id")
    val manga: Manga
)
