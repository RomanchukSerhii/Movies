package com.example.movies.data.network.json_object.review

import com.squareup.moshi.Json

data class Author (
    @Json(name = "username") val name: String,
    @Json(name = "rating") val rating: Int?
)