package com.example.petminder.models

interface FeedStore {
    fun findAll(): List<FeedModel>
    fun create(feed: FeedModel)
    fun update(feed: FeedModel)
    fun findByPet(petId: Long): List<FeedModel>
}