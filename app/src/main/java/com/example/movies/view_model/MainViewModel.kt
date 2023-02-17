package com.example.movies.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.network.json_object.movie.Movie
import com.example.movies.data.network.MovieApiFactory
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var page = FIRST_PAGE_NUMBER

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val loadedMovies = _movies.value?.toMutableList()
                val movieResponse = MovieApiFactory.retrofitService.loadMovies(page)

                if (loadedMovies != null) {
                    loadedMovies.addAll(movieResponse.movies)
                    _movies.value = loadedMovies.toList()
                } else {
                    _movies.value = movieResponse.movies
                }
                _isLoading.value = false
                page++
            } catch (e: RuntimeException) {
                Log.d(TAG, e.toString())
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
        private const val FIRST_PAGE_NUMBER = 1
    }
}