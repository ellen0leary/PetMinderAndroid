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
        var edit=false

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        i("Pet Minder Activity started....")

        if(intent.hasExtra("pet_edit")){
            edit = true
            pet = intent.extras?.getParcelable("pet_edit")!!
            binding.petName.setText(pet.name)
            binding.btnAdd.setText(R.string.save_pet)
        }

        binding.btnAdd.setOnClickListener(){
            pet.name = binding.petName.text.toString()
            if (pet.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_pet_title, Snackbar.LENGTH_SHORT).show()
            } else{
                if (edit) {
                    app.pets.update(pet.copy())
                } else {
                    app.pets.create(pet.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
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