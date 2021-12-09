package com.example.petminder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petminder.R
import com.example.petminder.main.MainApp

class PetListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_list)
        app = application as MainApp
    }
}