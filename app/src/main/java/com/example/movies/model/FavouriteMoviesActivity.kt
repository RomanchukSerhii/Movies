package com.example.movies.model

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.movies.data.network.json_object.movie.Movie
import com.example.movies.databinding.ActivityFavouriteMoviesBinding
import com.example.movies.model.adapters.MoviesAdapter
import com.example.movies.view_model.FavouriteMoviesViewModel

class FavouriteMoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteMoviesBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var viewModel: FavouriteMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviesAdapter = MoviesAdapter ({ onItemClicked(it)}, {})
        viewModel = ViewModelProvider(this)[FavouriteMoviesViewModel::class.java]
        viewModel.getFavouriteMovies().observe(this) {
            moviesAdapter.submitList(it)
        }
        binding.recyclerViewFavouriteMovies.adapter = moviesAdapter
    }

    private fun onItemClicked(movie: Movie) {
        startActivity(
            MovieDetailActivity.getIntentMovieDetail(this, movie)
        )
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, FavouriteMoviesActivity::class.java)
        }
    }
}