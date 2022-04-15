package com.example.petminder.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petminder.firebase.db.FeedDBManager
import com.example.petminder.firebase.db.PetDBManager
import com.example.petminder.models.exercises.ExerciseManager
import com.example.petminder.models.exercises.ExerciseModel
import com.example.petminder.models.feeds.FeedManager
import com.example.petminder.models.feeds.FeedModel
import com.google.firebase.auth.FirebaseUser
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
            Timber.i("Report Load Success : ${feedList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }
}