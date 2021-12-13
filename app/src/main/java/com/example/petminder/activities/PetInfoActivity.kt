package com.example.petminder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petminder.databinding.ActivityInfoPetBinding
import com.example.petminder.databinding.ActivityPetBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.PetModel
import com.squareup.picasso.Picasso

class PetInfoActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityInfoPetBinding
    var pet = PetModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        //the pet
        pet  = intent.extras?.getParcelable("pet_info")!!

        Picasso.get().load(pet.image).into(binding.petImage)
        //name
    }
}