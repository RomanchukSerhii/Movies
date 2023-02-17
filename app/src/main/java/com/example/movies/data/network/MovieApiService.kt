package com.example.movies.data.network

import com.example.movies.data.network.json_object.movie.MovieResponse
import com.example.movies.data.network.json_object.review.ReviewResponse
import com.example.movies.data.network.json_object.video.VideoResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL =
    "https://api.themoviedb.org/3/movie/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MovieApiService {
    @GET("top_rated?api_key=3c8f22ace537ae4fea260fdfb55e301b&language=ru-RUS")
    suspend fun loadMovies(@Query("page") page: Int): MovieResponse

    @GET("{movie_id}/videos?api_key=3c8f22ace537ae4fea260fdfb55e301b&language=en-US")
    suspend fun loadVideos(@Path("movie_id") movieId: Int): VideoResponse

    @GET("{movie_id}/reviews?api_key=3c8f22ace537ae4fea260fdfb55e301b&language=en-US")
    suspend fun loadReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): ReviewResponse
}

object MovieApiFactory {
    val retrofitService: MovieApiService by lazy { retrofit.create(MovieApiService::class.java)}
}