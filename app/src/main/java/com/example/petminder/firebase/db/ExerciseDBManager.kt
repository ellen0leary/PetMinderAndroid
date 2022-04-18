package com.example.petminder.firebase.db

import androidx.lifecycle.MutableLiveData
import com.example.petminder.models.exercises.ExerciseModel
import com.example.petminder.models.exercises.ExerciseStore
import com.example.petminder.models.feeds.FeedModel
import com.google.firebase.database.*
import timber.log.Timber

object ExerciseDBManager:ExerciseStore {
    val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(id: String, exerciseList: MutableLiveData<List<ExerciseModel>>) {
        Timber.i(id)
        FeedDBManager.database.child("exercises").child(id)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Donation error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<ExerciseModel>()
                    val children = snapshot.children
                    children.forEach {
                        val feed = it.getValue(ExerciseModel::class.java)
                        localList.add(feed!!)
                    }
                    database.child("feeds").child(id)
                        .removeEventListener(this)

                    exerciseList.value = localList
                }
            })
    }

    override fun create(petId: String, exercise: ExerciseModel) {
        Timber.i("Firebase DB Reference : ${database}")

        val key = database.child("exercises").push().key
        if(key==null){
            Timber.i("Firebase Error: Key Empty")
            return
        }

        exercise.uid = key
        val petiD = exercise.petId
        val exerciseValues = exercise.toMap()
        val childAdd = HashMap<String, Any>()
        childAdd["exercises/$petiD/$key"] = exerciseValues
        database.updateChildren(childAdd)
    }

    override fun update(petId: String, exercise: ExerciseModel) {
        val exerciseValue = exercise.toMap()
        val childUpdate: MutableMap<String, Any?> = HashMap()
        val petid = exercise.petId
        val exerciseiD = exercise.uid
        childUpdate["exercises/$petid/$exerciseiD"] = exerciseValue
        database.updateChildren(childUpdate)
    }

    override fun deleteOne(petId: String, exerciseId: String) {
        val childDelete: MutableMap<String, Any?> = HashMap()
        childDelete["exercises/$petId/$exerciseId"] = null
        database.updateChildren(childDelete)
    }
}