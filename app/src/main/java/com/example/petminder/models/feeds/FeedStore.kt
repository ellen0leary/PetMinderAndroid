package com.example.petminder.models.feeds

import androidx.lifecycle.MutableLiveData

interface FeedStore {
    fun findAll(feedList: MutableLiveData<List<FeedModel>>)
    fun create(feed: FeedModel)
    fun update(feed: FeedModel)
    fun findByPet(petId: String): List<FeedModel>
    fun deleteOne(feedId: String)
}