package com.example.petminder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petminder.databinding.CardFeedBinding
import com.example.petminder.models.FeedModel
import com.example.petminder.models.PetModel
import com.squareup.picasso.Picasso

interface FeedListener {
    fun onFeedClick(feed: FeedModel)
}

class FeedAdapter constructor(private var Feeds: List<FeedModel>, private val listener: FeedListener) :
    RecyclerView.Adapter<FeedAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardFeedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val Feed = Feeds[holder.adapterPosition]
        holder.bind(Feed, listener)
    }

    override fun getItemCount(): Int = Feeds.size

    class MainHolder(private val binding : CardFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(feed: FeedModel, listener: FeedListener) {
//            binding.PetName.text = pet.name
//            Picasso.get()
//                .load(pet.image)
//                .centerCrop()
//                .resize(360,360) //resize image
//                .into(binding.imageView)

            binding.feedTime.text = feed.time
            binding.feedWeight.text = feed.weigth.toString()
            binding.root.setOnClickListener{listener.onFeedClick(feed)}
        }
    }
}


