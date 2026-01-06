package com.example.booxplore.data.remote

import com.example.booxplore.data.remote.dto.FictionResponseDto
import com.example.booxplore.data.remote.dto.SearchResponseDto
import com.example.booxplore.data.remote.dto.WorkDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenLibraryApi {

    @GET("subjects/fiction.json")
    suspend fun getFictionBooks(
        @Query("limit") limit: Int = 20
    ): FictionResponseDto

    @GET("works/{workId}.json")
    suspend fun getWorkDetails(
        @Path("workId") workId: String
    ): WorkDto

    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit: Int = 20
    ): SearchResponseDto
}