package com.example.petminder.firebase.db

import androidx.lifecycle.MutableLiveData
import com.example.petminder.models.exercises.ExerciseModel
import com.example.petminder.models.exercises.ExerciseStore
import com.google.firebase.database.*
import timber.log.Timber

object ExerciseDBManager:ExerciseStore {
    val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(id: String, exerciseList: MutableLiveData<List<ExerciseModel>>) {
        database.child("exercises").child(id)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<ExerciseModel>()
                    val children = snapshot.children
                    children.forEach{
                        val exercise = it.getValue(ExerciseModel::class.java)
                        localList.add(exercise!!)
                    }
                    database.child("exercises").child(id)
                        .removeEventListener(this)
                }

                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Exercise error : ${error.message}")
                }

            })
    }

    override fun create(petId: String, exercise: ExerciseModel) {
//        TODO("Not yet implemented")
        Timber.i("Firebase DB Reference : ${database}")

        val key = database.child("exercises").push().key
        if(key==null){
            Timber.i("Firebase Error: Key Empty")
            return
        }

        exercise.uid = key
        val exerciseValues = exercise.toMap()
        val childAdd = HashMap<String, Any>()
        childAdd["exercises/$petId/$key"] = exerciseValues
        database.updateChildren(childAdd)
    }

    override fun update(petId: String, exercise: ExerciseModel) {
//        TODO("Not yet implemented")
        val exerciseValue = exercise.toMap()
        val childUpdate: MutableMap<String, Any?> = HashMap()
        val exerciseId = exercise.petId
        childUpdate["exercises/$petId/$exerciseId"] = exerciseValue
        database.updateChildren(childUpdate)
    }

    override fun deleteOne(petId: String, exerciseId: String) {
//        TODO("Not yet implemented")
        val childDelete: MutableMap<String, Any?> = HashMap()
        childDelete["exercises/$petId/$exerciseId"] = null
        database.updateChildren(childDelete)
    }
}