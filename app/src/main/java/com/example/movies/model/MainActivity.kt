package com.example.movies.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.movies.R
import com.example.movies.view_model.MainViewModel
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.model.adapters.MoviesAdapter
import com.example.movies.data.network.json_object.movie.Movie

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviesAdapter = MoviesAdapter ({ onItemClicked(it) }, {onReachEndListener()})
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.movies.observe(this) { moviesAdapter.submitList(it) }
        binding.recyclerViewMovies.adapter = moviesAdapter

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBarLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemFavouriteMovies) {
            val intent = FavouriteMoviesActivity.getIntent(this)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onItemClicked(movie: Movie) {
        startActivity(MovieDetailActivity.getIntentMovieDetail(this, movie))
    }

    private fun onReachEndListener() { viewModel.loadMovies() }

    companion object {
        private const val TAG = "MainActivity"
    }
}