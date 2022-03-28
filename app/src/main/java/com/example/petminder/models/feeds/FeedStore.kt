package com.example.petminder.models.feeds

interface FeedStore {
    fun findAll(): List<FeedModel>
    fun create(feed: FeedModel)
    fun update(feed: FeedModel)
    fun findByPet(petId: Long): List<FeedModel>
    fun deleteOne(feedId: Long)
}