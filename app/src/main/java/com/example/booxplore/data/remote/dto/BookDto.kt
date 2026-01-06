package com.example.booxplore.data.remote.dto;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable;


@Serializable
data class BookDto(
    val key: String,
    val title: String,
    val authors: List<AuthorDto> = emptyList(),
    @SerialName("cover_id")
    val coverId: Int? = null,
    @SerialName("cover_i")
    val coverIdSearch: Int? = null,
    @SerialName("first_publish_year")
    val firstPublishYear: Int? = null
)