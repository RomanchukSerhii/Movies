package com.example.movies.data.network.json_object.review

import com.example.movies.data.network.json_object.review.Author
import com.squareup.moshi.Json

data class Review (
    @Json(name = "author_details") val author: Author,
    @Json(name = "content") val review: String
)
