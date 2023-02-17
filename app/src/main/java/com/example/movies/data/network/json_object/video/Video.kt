package com.example.movies.data.network.json_object.video

import com.squareup.moshi.Json

data class Video(
    @Json(name = "name") val name: String,
    @Json(name = "key") val keyVideo: String,
    @Json(name = "site") val siteName: String,
    @Json(name = "type") val type: String
)