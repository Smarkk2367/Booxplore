package com.example.booxplore.data.remote

import com.example.booxplore.data.remote.dto.BooksResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    @GET("subjects/fiction.json")
    suspend fun getFictionBooks(
        @Query("limit") limit: Int = 20
    ): BooksResponseDto
}