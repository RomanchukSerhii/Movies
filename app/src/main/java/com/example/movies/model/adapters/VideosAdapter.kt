package com.example.movies.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.VideoItemBinding
import com.example.movies.data.network.json_object.video.Video

class VideosAdapter(
    private val onItemClicked: (video: Video) -> Unit
) : ListAdapter<Video, VideosAdapter.VideoItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemViewHolder {
        val binding = VideoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VideoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoItemViewHolder, position: Int) {
        val currentVideo = getItem(position)
        holder.bind(currentVideo)
        holder.itemView.setOnClickListener {
            onItemClicked(currentVideo)
        }
    }

    class VideoItemViewHolder(
        private val binding: VideoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video) {
            binding.textViewNameVideo.text = video.name
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}