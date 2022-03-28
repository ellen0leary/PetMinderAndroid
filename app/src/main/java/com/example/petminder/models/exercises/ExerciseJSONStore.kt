package com.example.petminder.models.exercises

import android.content.Context
import android.net.Uri
import com.example.petminder.helpers.UriParser
import com.example.petminder.helpers.exists
import com.example.petminder.helpers.read
import com.example.petminder.helpers.write
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


const val  EXERCISE_JSON_FILE = "exercises.json"
val exerciseGsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val exerciselistType = object : TypeToken<ArrayList<ExerciseModel>>() {}.type

fun generateRandomExerciseId() : Long{
    return  Random().nextLong()
}
class ExerciseJSONStore(private val context: Context) : ExerciseStore {
    var exercises = mutableListOf<ExerciseModel>()


    init {
        if (exists(context, EXERCISE_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<ExerciseModel> {
        logAll()
        return exercises
    }

    override fun create(exercise: ExerciseModel) {
        exercise.id = generateRandomExerciseId()
        exercises.add(exercise)
        serialize()
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
        serialize()
    }


    override fun update(exercise: ExerciseModel) {
        var foundExercise: ExerciseModel? = exercises.find { p-> p.id == exercise.id}
        if(foundExercise != null) {
            foundExercise.name = exercise.name
            foundExercise.length = exercise.length
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = exerciseGsonBuilder.toJson(exercises, exerciselistType)
        write(context, EXERCISE_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, EXERCISE_JSON_FILE)
        exercises = exerciseGsonBuilder.fromJson(jsonString, exerciselistType)
    }

    private fun logAll() {
        exercises.forEach { Timber.i("$it") }
    }
}
