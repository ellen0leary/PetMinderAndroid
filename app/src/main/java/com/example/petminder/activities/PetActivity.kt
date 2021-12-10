package com.example.petminder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.petminder.R
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

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        i("Pet Minder Activity started....")

        binding.btnAdd.setOnClickListener(){
            pet.name = binding.petName.text.toString()
            if (pet.name.isNotEmpty()) {
                app.pets.create(pet.copy())
                i("add Button Pressed")
                setResult(RESULT_OK)
                finish()
            } else{
                Snackbar.make(it,"Please enter a name", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pet, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}