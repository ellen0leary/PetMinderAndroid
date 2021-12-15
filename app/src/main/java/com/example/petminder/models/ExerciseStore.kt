package com.example.petminder.models

interface ExerciseStore {
    fun findAll(): List<ExerciseModel>
    fun create(exercise: ExerciseModel)
    fun update(exercise: ExerciseModel)
    fun findByPet(petId: Long): List<ExerciseModel>
    fun deleteOne(exerciseId: Long)
}