package com.example.movies.model.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.MovieItemBinding
import com.example.movies.data.network.json_object.movie.Movie

class MoviesAdapter(
    private val onItemClicked: (movie: Movie) -> Unit,
    private val onReachEndListener: () -> Unit
) : ListAdapter<Movie, MoviesAdapter.MovieItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val currentMovie = getItem(position)
        holder.bind(currentMovie, holder.itemView)
        holder.itemView.setOnClickListener {
            onItemClicked(currentMovie)
        }
        if (position == currentList.size - 10) {
            onReachEndListener()
        }
    }

    class MovieItemViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, view: View) {
            binding.apply {
                val imageUrl = BASE_URL + movie.posterURL
                Glide.with(view)
                    .load(imageUrl)
                    .into(imageViewPoster)
                Log.d("MoviesAdapter", imageUrl)

                val rating = movie.rating.toDouble()
                val backgroundId = if (rating > 7) {
                    R.drawable.circle_green
                } else if (rating > 5) {
                    R.drawable.circle_yellow
                } else {
                    R.drawable.circle_red
                }
                val drawable = ContextCompat.getDrawable(view.context, backgroundId)
                textViewRating.background = drawable
                textViewRating.text = movie.rating
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://www.themoviedb.org/t/p/original"
        private val DiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}