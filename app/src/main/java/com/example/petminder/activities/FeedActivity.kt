package com.example.petminder.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.petminder.R
import com.example.petminder.databinding.ActivityFeedBinding
import com.example.petminder.databinding.ActivityPetBinding
import com.example.petminder.helpers.showImagePicker
import com.example.petminder.main.MainApp
import com.example.petminder.models.PetModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber

class FeedActivity : AppCompatActivity(){

    private lateinit var binding: ActivityFeedBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var pet = PetModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var edit=false

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        Timber.i("Feed Activity started....")


        binding.btnAdd.setOnClickListener(){
            pet.name = binding.petName.text.toString()
            pet.weight = binding.petWeight.text.toString().toFloat()
            pet.age = binding.petAge.text.toString().toInt()
            if (pet.name.isEmpty()) {
                Snackbar.make(it, R.string.enter_pet_title, Snackbar.LENGTH_SHORT).show()
            } else{
                if (edit) {
                    app.pets.update(pet.copy())
                    Timber.i(pet.toString())

                } else {
                    app.pets.create(pet.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }

    }
}