package com.example.booxplore.data.model

data class BooksResponseDto(
    val works: List<BookDto>
)

data class BookDto(
    val key: String,
    val title: String,
    val cover_id: Int?,
    val authors: List<AuthorDto>?
)

data class AuthorDto(
    val name: String
)