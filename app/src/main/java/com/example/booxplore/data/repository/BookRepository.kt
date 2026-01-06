package com.example.booxplore.data.repository

import com.example.booxplore.data.domain.model.Book
import com.example.booxplore.data.mapper.toDomain
import com.example.booxplore.data.remote.OpenLibraryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepository(
    private val api: OpenLibraryApi
) {
    suspend fun getFictionBooks(): Result<List<Book>> =
        withContext(Dispatchers.IO) {
            runCatching {
                api.getFictionBooks()
                    .works
                    .map { it.toDomain() }
            }
        }

    suspend fun getBookDetails(id: String): Result<Book> =
        withContext(Dispatchers.IO) {
            runCatching {
                val workDto = api.getWorkDetails(id)
                val coverId = workDto.covers?.firstOrNull()
                Book(
                    id = id,
                    title = workDto.title,
                    authors = "See details",
                    coverUrl = coverId?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" },
                    publishYear = null,
                    description = workDto.description,
                    largeCoverUrl = coverId?.let { "https://covers.openlibrary.org/b/id/$it-L.jpg" },
                    pages = workDto.numberOfPages
                )
            }
        }

    suspend fun searchBooks(query: String): Result<List<Book>> =
        withContext(Dispatchers.IO) {
            runCatching {
                api.searchBooks(query)
                    .docs
                    .map { it.toDomain() }
            }
        }
}