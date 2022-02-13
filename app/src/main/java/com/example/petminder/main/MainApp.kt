package com.example.petminder.main

import android.app.Application
import com.example.petminder.models.*
import com.github.ajalt.timberkt.Timber
import timber.log.Timber.i

class MainApp: Application() {


    lateinit var pets: PetStore
    lateinit var feeds: FeedStore
    lateinit var exercises: ExerciseStore

    override fun onCreate(){
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        pets  = PetJSONStore(applicationContext)
        feeds = FeedJSONStore(applicationContext)
        exercises = ExerciseJSONStore(applicationContext)
        i("Pet started")
    }
}