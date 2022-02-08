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

// ToDo : searching
// ToDo : add an api - https://github.com/public-apis/public-apis#animals
// ToDo : cloud storage
// ToDo : different ui elements - idk
// ToDo : different Nav - tabs

// ToDo [Bug] - fix adding images in fragemets- petAdd
// ToDo [Bug] - pet Click on petList