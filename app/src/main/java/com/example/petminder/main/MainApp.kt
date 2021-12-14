package com.example.petminder.main

import android.app.Application
import com.example.petminder.models.*
import com.github.ajalt.timberkt.Timber
import timber.log.Timber.i

class MainApp: Application() {

//    val pets = ArrayList<PetModel>()
    lateinit var pets: PetStore
    lateinit var feeds: FeedStore

    override fun onCreate(){
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        pets  = PetJSONStore(applicationContext)
        feeds = FeedJSONStore(applicationContext)
//        pets = PetMemStore()
        i("Pet started")
    }
}