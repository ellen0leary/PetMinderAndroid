package com.example.petminder.models.exercises

import com.example.petminder.helpers.exists
import com.example.petminder.helpers.read
import com.example.petminder.helpers.write
import timber.log.Timber

class ExerciseManager: ExerciseStore {
    var exercises = ArrayList<ExerciseModel>()

    override fun findAll(): MutableList<ExerciseModel> {
        return exercises
    }

    override fun create(exercise: ExerciseModel) {
        exercise.id = generateRandomExerciseId()
        exercises.add(exercise)
    }

    override fun findByPet(petId: Long): List<ExerciseModel> {
        val list = java.util.ArrayList<ExerciseModel>()
        for(i in exercises){
            if(i.petId == petId){
                list.add(i)
            }
        }
        return list
    }

    fun findOne(id: Long): ExerciseModel? {
        var foundExercise : ExerciseModel? = exercises.find{ p -> p.id == id}
        return foundExercise
    }

    override fun deleteOne(exerciseId: Long) {
        val foundExercise = findOne(exerciseId)
        if(foundExercise!= null){
            exercises.remove(foundExercise)
        }
    }


    override fun update(exercise: ExerciseModel) {
        var foundExercise: ExerciseModel? = exercises.find { p-> p.id == exercise.id}
        if(foundExercise != null) {
            foundExercise.name = exercise.name
            foundExercise.length = exercise.length
        }
    }
}