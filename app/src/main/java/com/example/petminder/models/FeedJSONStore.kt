package com.example.petminder.models

import android.content.Context
import android.net.Uri
import com.example.petminder.helpers.UriParser
import com.example.petminder.helpers.exists
import com.example.petminder.helpers.read
import com.example.petminder.helpers.write
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

const val  FEED_JSON_FILE = "feeds.json"
val feedGsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val feedlistType = object : TypeToken<ArrayList<FeedModel>>() {}.type

fun generateRandomFeedId() : Long{
    return  Random().nextLong()
}
class FeedJSONStore(private val context: Context) : FeedStore{
    var feeds = mutableListOf<FeedModel>()


    init {
        if (exists(context, FEED_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<FeedModel> {
        logAll()
        return feeds
    }

    override fun create(feed: FeedModel) {
        feed.id = generateRandomFeedId()
        feeds.add(feed)
        serialize()
    }

    override fun findByPet(petId: Long): List<FeedModel> {
        val list = java.util.ArrayList<FeedModel>()
        for(i in feeds){
            if(i.petId == petId){
                list.add(i)
            }
        }
        return list
    }


    override fun update(feed: FeedModel) {
        var foundFeed: FeedModel? = feeds.find {p-> p.id == feed.id}
        if(foundFeed != null) {
            foundFeed.time = feed.time
            foundFeed.weigth = feed.weigth
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = feedGsonBuilder.toJson(feeds, feedlistType)
        write(context, FEED_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, FEED_JSON_FILE)
        feeds = feedGsonBuilder.fromJson(jsonString, feedlistType)
    }

    private fun logAll() {
        feeds.forEach { Timber.i("$it") }
    }
}
