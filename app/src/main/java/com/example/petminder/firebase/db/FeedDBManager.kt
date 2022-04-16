package com.example.petminder.firebase.db

import androidx.lifecycle.MutableLiveData
import com.example.petminder.models.feeds.FeedModel
import com.example.petminder.models.feeds.FeedStore
import com.example.petminder.models.pets.PetModel
import com.google.firebase.database.*
import timber.log.Timber

object FeedDBManager: FeedStore {
    val database : DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun findAll(id :String,feedList: MutableLiveData<List<FeedModel>>) {
        Timber.i(id)
        database.child("feeds").child(id)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Donation error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<FeedModel>()
                    val children = snapshot.children
                    children.forEach {
                        val feed = it.getValue(FeedModel::class.java)
                        localList.add(feed!!)
                    }
                    database.child("feeds").child(id)
                        .removeEventListener(this)

                    feedList.value = localList
                }
            })
    }

    override fun create(petId: String,feed: FeedModel) {
        Timber.i("Firebase DB Reference : ${database}")

        val key = database.child("feeds").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        feed.uid = key
        val feedValues = feed.toMap()
        val petiD = feed.petId

        val childAdd = HashMap<String, Any>()
        childAdd["/feeds/$petiD/$key"] = feedValues

        database.updateChildren(childAdd)

    }

    override fun update(petId: String,feed: FeedModel) {
        val feedValue = feed.toMap()
        val childUpdate: MutableMap<String, Any?> = HashMap()
        val feedId = feed.uid
        childUpdate["feeds/$petId/$feedId"] = feedValue
        database.updateChildren(childUpdate)
    }

    override fun findByPet(petId: String): List<FeedModel> {
        TODO("Not yet implemented")
    }

    override fun deleteOne(petId: String,feedId: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/feeds/$petId/$feedId"] = null

        database.updateChildren(childDelete)
    }
}