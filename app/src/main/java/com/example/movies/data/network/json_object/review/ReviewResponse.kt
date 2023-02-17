package com.example.movies.data.network.json_object.review

import com.example.movies.data.network.json_object.review.Review
import com.squareup.moshi.Json

data class ReviewResponse(
    @Json(name = "results") val reviews: List<Review>
)