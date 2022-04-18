package com.example.petminder.ui.exceriseAdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petminder.firebase.db.ExerciseDBManager
import com.example.petminder.models.exercises.ExerciseModel
import java.lang.IllegalArgumentException

class ExerciseViewModel: ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatis: LiveData<Boolean>
        get() = status

    fun addExercise(petId :String,exercise: ExerciseModel){
        status.value = try{
            ExerciseDBManager.create(petId,exercise)
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }

    fun updateExercise(petId :String,exercise: ExerciseModel){
        status.value = try{
            ExerciseDBManager.update(petId,exercise)
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }
}