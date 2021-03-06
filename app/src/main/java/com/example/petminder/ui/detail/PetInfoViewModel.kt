package com.example.petminder.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petminder.firebase.db.ExerciseDBManager
import com.example.petminder.firebase.db.FeedDBManager
import com.example.petminder.models.exercises.ExerciseModel
import com.example.petminder.models.feeds.FeedModel
import timber.log.Timber
import java.lang.Exception

class PetInfoViewModel: ViewModel() {

    private val exerciseList = MutableLiveData<List<ExerciseModel>>()
    private val feedList = MutableLiveData<List<FeedModel>>()

    val observableFeedList: LiveData<List<FeedModel>>
        get() = feedList

    val observableExerciseList: LiveData<List<ExerciseModel>>
        get() = exerciseList

    var observalePet =  MutableLiveData<String>()

    init {
//        load()
    }

    fun load() {
        Timber.i("Pet id ${observalePet.value}")
        try {
            FeedDBManager.findAll(observalePet.value!!, feedList)
            Timber.i("Report Feed Load Success : ${feedList.value!!.size.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Feed Load Error : $e.message")
        }
    }

    fun loadExe() {
        Timber.i("Pet id ${observalePet.value}")
        try {
            ExerciseDBManager.findAll(observalePet.value!!, exerciseList)
            Timber.i("Report Exercise Load Success : ${exerciseList.value!!.size.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Exercise Load Error : $e.message")
        }
    }

    fun delete(petId: String, feedId: String) {
        try {
            FeedDBManager.deleteOne(petId,feedId)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}