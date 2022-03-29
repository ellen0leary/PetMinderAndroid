package com.example.petminder.ui.exceriseAdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petminder.models.exercises.ExerciseManager
import com.example.petminder.models.exercises.ExerciseModel
import com.example.petminder.models.pets.PetManager
import com.example.petminder.models.pets.PetModel
import java.lang.IllegalArgumentException

class ExerciseViewModel: ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatis: LiveData<Boolean>
        get() = status

    fun addExercise(exercise: ExerciseModel){
        status.value = try{
            ExerciseManager.create(exercise)
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }
}