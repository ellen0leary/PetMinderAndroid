package com.example.petminder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petminder.databinding.ActivityInfoPetBinding
import com.example.petminder.databinding.ActivityPetBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.PetModel
import com.squareup.picasso.Picasso
import timber.log.Timber.i

class PetInfoActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityInfoPetBinding
    var pet = PetModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //the pet
        pet  = intent.extras?.getParcelable("pet_info")!!

        binding.toolbarAdd.title = pet.name
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp


        Picasso.get().load(pet.image).into(binding.petImage)
        val ageText = "Age - " + pet.age.toString()
        binding.ageText.setText(ageText)

        val weightText = "Weight - " + pet.weight.toString()
        binding.weightText.setText(weightText)

        //name
    }
}