package com.example.booxplore.data.remote.dto

data class BookDto(
    val key: String,
    val title: String,
    val cover_id: Int?,
    val authors: List<AuthorDto>?
)