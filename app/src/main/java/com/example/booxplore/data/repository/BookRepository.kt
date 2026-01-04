package com.example.booxplore.data.repository

import com.example.booxplore.data.remote.BookApi
import com.example.booxplore.data.remote.RetrofitInstance
import com.example.booxplore.data.remote.dto.BookDto

class BookRepository(private val api: BookApi = RetrofitInstance.api) {

    suspend fun getFictionBooks(): Result<List<BookDto>> {
        return try {
            val response = api.getFictionBooks()
            Result.success(response.works)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}