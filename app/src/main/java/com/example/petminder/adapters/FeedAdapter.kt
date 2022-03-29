package com.example.petminder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petminder.databinding.CardFeedBinding
import com.example.petminder.models.feeds.FeedModel

interface FeedListener {
    fun onFeedClick(feed: FeedModel)
}

class FeedAdapter constructor(private var feeds: List<FeedModel>, private val listener: FeedListener) :
    RecyclerView.Adapter<FeedAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardFeedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val feed = feeds[holder.adapterPosition]
        holder.bind(feed, listener)
    }

    override fun getItemCount(): Int = feeds.size

    class MainHolder(private val binding : CardFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(feed: FeedModel, listener: FeedListener) {
//            binding.feedTime.text = feed.time
//            binding.feedWeight.text = feed.weigth.toString()
            binding.feed = feed
            binding.root.setOnClickListener{listener.onFeedClick(feed)}
            binding.executePendingBindings()
        }
    }
}


