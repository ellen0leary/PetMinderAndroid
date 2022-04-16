package com.example.petminder.models.exercises

import androidx.lifecycle.MutableLiveData

interface ExerciseStore {
    fun findAll(id: String, exerciseList: MutableLiveData<List<ExerciseModel>>)

    fun create(petId: String,exercise: ExerciseModel)
    fun update(petId: String,exercise: ExerciseModel)
    fun deleteOne(petId: String,exerciseId: String)
}