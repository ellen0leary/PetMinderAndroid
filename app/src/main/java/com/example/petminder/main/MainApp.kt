package com.example.petminder.main

import android.app.Application
import android.content.Context
import com.example.petminder.models.exercises.ExerciseStore
import com.example.petminder.models.feeds.FeedStore
import com.example.petminder.models.pets.PetStore
import com.github.ajalt.timberkt.Timber
import timber.log.Timber.i

class MainApp: Application() {

    override fun onCreate(){
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Pet started")
    }

}