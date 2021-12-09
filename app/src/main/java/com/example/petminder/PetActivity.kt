package com.example.petminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.ajalt.timberkt.Timber
import timber.log.Timber.i

class PetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet)

        Timber.plant(Timber.DebugTree())
        i("Pet Minder Activity started....")
    }
}