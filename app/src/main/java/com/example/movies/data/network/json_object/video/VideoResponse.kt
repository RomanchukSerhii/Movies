package com.example.movies.data.network.json_object.video

import com.example.movies.data.network.json_object.video.Video
import com.squareup.moshi.Json

data class VideoResponse(
    @Json(name = "results") val video: List<Video>
)