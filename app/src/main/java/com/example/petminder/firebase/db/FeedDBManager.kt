package com.example.petminder.firebase.db

import androidx.lifecycle.MutableLiveData
import com.example.petminder.models.feeds.FeedModel
import com.example.petminder.models.feeds.FeedStore
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

object FeedDBManager: FeedStore {
    val database : DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun findAll(feedList: MutableLiveData<List<FeedModel>>) {
        TODO("Not yet implemented")
    }

    override fun create(petId: String,feed: FeedModel) {
        Timber.i("Firebase DB Reference : ${PetDBManager.database}")

        val key = PetDBManager.database.child("feeds").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        feed.uid = key
        val feedValues = feed.toMap()
        val petiD = feed.petId

        val childAdd = HashMap<String, Any>()
        childAdd["/feeds/$petiD/$key"] = feedValues
//        childAdd["/user-pets/$uid/$key"] = petValues

        PetDBManager.database.updateChildren(childAdd)

    }

    override fun update(feed: FeedModel) {
        TODO("Not yet implemented")
    }

    override fun findByPet(petId: String): List<FeedModel> {
        TODO("Not yet implemented")
    }

    override fun deleteOne(feedId: String) {
        TODO("Not yet implemented")
    }
}