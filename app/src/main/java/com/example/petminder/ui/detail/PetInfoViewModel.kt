package com.example.petminder.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petminder.models.exercises.ExerciseManager
import com.example.petminder.models.exercises.ExerciseModel
import com.example.petminder.models.feeds.FeedManager
import com.example.petminder.models.feeds.FeedModel

class PetInfoViewModel: ViewModel() {

    private val exerciseList = MutableLiveData<List<ExerciseModel>>()
    private val feedList = MutableLiveData<List<FeedModel>>()

    val observableFeedList: LiveData<List<FeedModel>>
        get() = feedList

    val observableExerciseList: LiveData<List<ExerciseModel>>
        get() = exerciseList

    init {
        load()
    }

    fun load() {
        feedList.value = FeedManager.findAll()
        exerciseList.value = ExerciseManager.findAll()
    }
}