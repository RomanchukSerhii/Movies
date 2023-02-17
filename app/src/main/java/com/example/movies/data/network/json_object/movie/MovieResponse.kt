package com.example.movies.data.network.json_object.movie

import com.example.movies.data.network.json_object.movie.Movie
import com.squareup.moshi.Json

data class MovieResponse(
    @Json(name = "results") val movies: List<Movie>
)