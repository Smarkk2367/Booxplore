package com.example.booxplore.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FictionResponseDto(
    @SerialName("works")
    val works: List<BookDto>
)