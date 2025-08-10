package dev.madad.mangalibbackend.entity

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OrderColumn
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

/**
 * JPA-сущность, представляющая главу манги.
 *
 * @property id Уникальный идентификатор главы.
 * @property chapterNum Номер или порядок главы.
 * @property title Название или заголовок главы.
 * @property pagesImgUrls Список URL изображений страниц главы.
 * @property manga Манга, к которой принадлежит данная глава.
 */
@Entity
@Table(
    name = "chapter",
    uniqueConstraints = [UniqueConstraint(name = "uk_chapter_manga_num", columnNames = ["manga_id", "chapter_num"])],
    indexes = [Index(name = "ix_chapter_manga_id", columnList = "manga_id")]
)
data class Chapter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "chapter_num", nullable = false)
    val chapterNum: Int = 0,

    @Column(nullable = false, length = 255)
    val title: String = "",

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "chapter_pages", joinColumns = [JoinColumn(name = "chapter_id")])
    @OrderColumn(name = "page_num")
    @Column(name = "image_url", length = 500, nullable = false)
    val pagesImgUrls: List<String> = emptyList(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manga_id", nullable = false)
    val manga: Manga
)
