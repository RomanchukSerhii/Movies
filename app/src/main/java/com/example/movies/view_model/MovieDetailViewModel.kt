package com.example.movies.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movies.data.database.MovieDatabase
import com.example.movies.data.network.MovieApiFactory
import com.example.movies.data.network.json_object.movie.Movie
import com.example.movies.data.network.json_object.review.Review
import com.example.movies.data.network.json_object.video.Video
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDao = MovieDatabase.getDatabase(application).movieDao()

    private var page = FIRST_PAGE_NUMBER
    private val movieApi = MovieApiFactory.retrofitService

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> = _videos

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    fun getFavouriteMovie(movieId: Int): LiveData<Movie> {
        return movieDao.getFavouriteMovie(movieId)
    }

    fun loadVideos(id: Int) {
        viewModelScope.launch {
            try {
                val listVideo = movieApi.loadVideos(id).video
                _videos.value = listVideo
            } catch (e: RuntimeException) {
                Log.d(TAG, e.toString())
            }
        }
    }

    fun loadReviews(id: Int) {
        viewModelScope.launch {
            try {
                val listReview = movieApi.loadReviews(id, page).reviews
                _reviews.value = listReview
            } catch (e: RuntimeException) {
                Log.d(TAG, e.toString())
            }
            page++
        }
    }

    fun insertToFavouriteMovies(movie: Movie) {
        viewModelScope.launch {
            movieDao.insertMovie(movie)
        }
    }

    fun deleteFromFavouriteMovies(movieId: Int) {
        viewModelScope.launch {
            movieDao.deleteMovie(movieId)
        }
    }

    companion object {
        private const val TAG = "MovieDetailViewModel"
        private const val FIRST_PAGE_NUMBER = 1
    }
}