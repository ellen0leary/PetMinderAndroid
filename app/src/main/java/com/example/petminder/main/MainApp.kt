package com.example.petminder.main

import android.app.Application
import com.example.petminder.models.PetModel
import com.github.ajalt.timberkt.Timber
import timber.log.Timber.i

class MainApp: Application() {

    val pets = ArrayList<PetModel>()
    override fun onCreate(){
        super.onCreate()
        Timber.plant(
            Timber.DebugTree()
        )
        i("Placemark started")
//        pets.add(PetModel("Apollo"))
//        pets.add(PetModel("Princess"))
//        pets.add(PetModel("Spot"))
    }
}