package com.example.petminder.main

import android.app.Application
import com.example.petminder.models.PetJSONStore
import com.example.petminder.models.PetMemStore
import com.example.petminder.models.PetModel
import com.example.petminder.models.PetStore
import com.github.ajalt.timberkt.Timber
import timber.log.Timber.i

class MainApp: Application() {

//    val pets = ArrayList<PetModel>()
    lateinit var pets: PetStore

    override fun onCreate(){
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        pets  = PetJSONStore(applicationContext)
        i("Pet started")
    }
}