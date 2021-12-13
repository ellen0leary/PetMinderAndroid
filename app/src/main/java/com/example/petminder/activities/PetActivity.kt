package com.example.petminder.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.petminder.R
import com.example.petminder.databinding.ActivityPetBinding
import com.example.petminder.helpers.showImagePicker
import com.example.petminder.main.MainApp
import com.example.petminder.models.PetModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber.i

class PetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
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
            pet  = intent.extras?.getParcelable("pet_edit")!!
            binding.petName.setText(pet.name)
            binding.petWeight.setText(pet.weight.toString())
            binding.petAge.setText(pet.age.toString())
            binding.btnAdd.setText(R.string.save_pet)
            binding.chooseImage.setText(R.string.update_image)
            Picasso.get().load(pet.image).into(binding.petImage)
        }
        binding.btnAdd.setOnClickListener(){
            pet.name = binding.petName.text.toString()
            pet.weight = binding.petWeight.text.toString().toFloat()
            pet.age = binding.petAge.text.toString().toInt()
            if (pet.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_pet_title, Snackbar.LENGTH_SHORT).show()
            } else{
                if (edit) {
                    app.pets.update(pet.copy())
                    i(pet.toString())

                } else {
                    app.pets.create(pet.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        registerImagePickerCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            pet.image = result.data!!.data!!
                            Picasso.get()
                                .load(pet.image)
                                .into(binding.petImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}