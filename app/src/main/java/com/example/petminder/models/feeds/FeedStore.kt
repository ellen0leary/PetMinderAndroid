package com.example.petminder.models.feeds

import androidx.lifecycle.MutableLiveData

interface FeedStore {
    fun findAll(id :String,feedList: MutableLiveData<List<FeedModel>>)
    fun create(petId: String,feed: FeedModel)
    fun update(petId: String,feed: FeedModel)
    fun findByPet(petId: String): List<FeedModel>
    fun deleteOne(petId: String,feedId: String)
}