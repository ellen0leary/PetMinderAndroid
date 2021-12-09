package com.example.petminder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petminder.databinding.ActivityPetBinding
import com.example.petminder.models.PetModel
import com.github.ajalt.timberkt.Timber
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i

class PetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetBinding
    var pet = PetModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        i("Pet Minder Activity started....")
        binding.btnAdd.setOnClickListener(){
            pet.name = binding.petName.text.toString()
            if (pet.name.isNotEmpty()) {
                i("add Button Pressed")
            } else{
                Snackbar.make(it,"Please enter a name", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}