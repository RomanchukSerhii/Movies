package com.example.movies.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.movies.data.network.json_object.movie.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM favourite_movies")
    fun getAllFavouriteMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM favourite_movies WHERE id = :movieId")
    fun getFavouriteMovie(movieId: Int): LiveData<Movie>

    @Insert
    suspend fun insertMovie(movie: Movie)

    @Query("DELETE FROM favourite_movies WHERE id = :movieId")
    suspend fun deleteMovie(movieId: Int)
}