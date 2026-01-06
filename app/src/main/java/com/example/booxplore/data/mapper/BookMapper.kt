package com.example.booxplore.data.mapper

import com.example.booxplore.data.domain.model.Book
import com.example.booxplore.data.remote.dto.BookDto

fun BookDto.toDomain(): Book {
    return Book(
        id = key.removePrefix("/works/"),
        title = title,
        authors = authors.joinToString { it.name },
        coverUrl = (coverId ?: coverIdSearch)?.let {
            "https://covers.openlibrary.org/b/id/$it-M.jpg"
        },
        publishYear = firstPublishYear
    )
}