package com.example.petminder.models.exercises

interface ExerciseStore {
    fun findAll(): List<ExerciseModel>
    fun create(exercise: ExerciseModel)
    fun update(exercise: ExerciseModel)
    fun findByPet(petId: String): List<ExerciseModel>
    fun deleteOne(exerciseId: Long)
}