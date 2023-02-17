package com.example.movies.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ListAdapter
import com.example.movies.data.database.MovieDatabase
import com.example.movies.data.network.json_object.movie.Movie

class FavouriteMoviesViewModel(application: Application) : AndroidViewModel(application) {
    val movieDao = MovieDatabase.getDatabase(application).movieDao()

    fun getFavouriteMovies(): LiveData<List<Movie>> {
        return movieDao.getAllFavouriteMovies()
    }
}