package com.example.movies.model

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movies.view_model.MovieDetailViewModel
import com.example.movies.databinding.ActivityMovieDetailBinding
import com.example.movies.model.adapters.ReviewAdapter
import com.example.movies.model.adapters.VideosAdapter
import com.example.movies.data.network.json_object.movie.Movie
import com.example.movies.data.network.json_object.video.Video
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private lateinit var videosAdapter: VideosAdapter
    private lateinit var reviewAdapter: ReviewAdapter
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.extras?.getParcelable(KEY_MOVIE) as Movie?
        movieDetailViewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        videosAdapter = VideosAdapter { onItemClicked(it) }
        reviewAdapter = ReviewAdapter()

        if (movie != null) {
            bind(movie)
            setAdapters(movie)
            setFavouriteMovieImage(movie)
        }
    }

    private fun bind(movie: Movie) {
        val imageUrl = BASE_URL + movie.posterURL
        val year = movie.releaseDate.substring(0, 4)
        binding.apply {
            Glide.with(this@MovieDetailActivity)
                .load(imageUrl)
                .into(imageViewPoster)

            textViewTitle.text = movie.name
            textViewYear.text = year
            textViewDescription.text = movie.description
        }

    }

    private fun setAdapters(movie: Movie) {
        movieDetailViewModel.apply {
            loadVideos(movie.id)
            loadReviews(movie.id)
            videos.observe(this@MovieDetailActivity) {
                videosAdapter.submitList(it)
            }
            reviews.observe(this@MovieDetailActivity) {
                Log.d(TAG, it.joinToString())
                reviewAdapter.submitList(it)
            }
        }
        binding.recyclerViewVideos.adapter = videosAdapter
        binding.recyclerViewReviews.adapter = reviewAdapter
    }

    private fun setFavouriteMovieImage(movie: Movie) {
        val starOff = ContextCompat.getDrawable(this, android.R.drawable.star_big_off)
        val starOn = ContextCompat.getDrawable(this, android.R.drawable.star_big_on)

        movieDetailViewModel.getFavouriteMovie(movie.id).observe(this) { movieFromDb ->
//            val starImage = if (it == null)  starOff else starOn
//            binding.imageViewStar.setImageDrawable(starImage)
            binding.apply {
                if (movieFromDb == null) {
                    imageViewStar.setImageDrawable(starOff)
                    imageViewStar.setOnClickListener {
                        movieDetailViewModel.insertToFavouriteMovies(movie)
                    }
                } else {
                    imageViewStar.setImageDrawable(starOn)
                    imageViewStar.setOnClickListener {
                        movieDetailViewModel.deleteFromFavouriteMovies(movie.id)
                    }
                }
            }
        }
    }

    private fun onItemClicked(video: Video) {
        if (video.siteName == "YouTube") {
            val queryURL = Uri.parse("$SEARCH_PREFIX${video.keyVideo}")
            val intent = Intent(Intent.ACTION_VIEW, queryURL)
            startActivity(intent)
        }
    }

    companion object {
        private const val KEY_MOVIE = "movie"
        private const val TAG = "MovieDetailActivity"
        private const val BASE_URL = "https://www.themoviedb.org/t/p/original"
        private const val SEARCH_PREFIX = "https://www.youtube.com/watch?v="

        fun getIntentMovieDetail(context: Context, movie: Movie): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(KEY_MOVIE, movie)
            return intent
        }
    }
}