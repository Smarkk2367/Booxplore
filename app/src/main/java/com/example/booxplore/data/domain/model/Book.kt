package com.example.booxplore.data.domain.model

data class Book(
    val id: String,
    val title: String,
    val authors: String,
    val coverUrl: String?,
    val publishYear: Int?,
    val description: String? = null,
    val largeCoverUrl: String? = null,
    val pages: Int? = null
)