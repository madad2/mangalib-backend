package dev.madad.mangalibbackend.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy
import jakarta.persistence.Table

/**
 * JPA-сущность, представляющая мангу.
 *
 * @property id Уникальный идентификатор манги.
 * @property title Название манги.
 * @property description Описание манги.
 * @property coverImgUrl URL обложки манги.
 * @property chapters Список глав манги.
 */
@Entity
@Table(name = "manga")
data class Manga(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 255)
    val title: String = "",

    @Column(nullable = false)
    val description: String = "",

    @Column(nullable = false, name = "cover")
    val coverImgUrl: String = "",

    @OneToMany(
        mappedBy = "manga",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @OrderBy("chapterNum ASC")
    val chapters: MutableList<Chapter> = mutableListOf()
)
