package com.example.petminder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petminder.databinding.ActivityPetBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.PetModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i

class PetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetBinding
    var pet = PetModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Pet Minder Activity started....")
        binding.btnAdd.setOnClickListener(){
            pet.name = binding.petName.text.toString()
            if (pet.name.isNotEmpty()) {
                app.pets.add(pet.copy())
                i("add Button Pressed")
            } else{
                Snackbar.make(it,"Please enter a name", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}