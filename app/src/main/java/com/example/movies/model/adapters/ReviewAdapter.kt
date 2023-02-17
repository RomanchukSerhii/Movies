package com.example.movies.model.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.databinding.ReviewItemBinding
import com.example.movies.data.network.json_object.review.Review

class ReviewAdapter : ListAdapter<Review, ReviewAdapter.ReviewItemViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewItemViewHolder {
        val binding = ReviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewItemViewHolder, position: Int) {
        val currentReview = getItem(position)
        val context = holder.itemView.context
        holder.bind(currentReview, context)
    }

    class ReviewItemViewHolder(
        private val binding: ReviewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review, context: Context) {
            binding.apply {
                val rating = review.author.rating
                textViewAuthor.text = review.author.name
                textViewReview.text = review.review
                if (rating != null) {
                    val color = if (rating > 7) {
                        ContextCompat.getColor(context, android.R.color.holo_green_light)
                    } else {
                        ContextCompat.getColor(context, android.R.color.holo_red_light)
                    }
                    linearLayoutReview.setBackgroundColor(color)
                    textViewRating.text = context.getString(R.string.rating, review.author.rating)
                } else {
                    val color = ContextCompat.getColor(context, android.R.color.holo_orange_light)
                    linearLayoutReview.setBackgroundColor(color)
                    textViewRating.text = context.getString(R.string.null_rating)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.review == newItem.review
            }
        }
    }
}