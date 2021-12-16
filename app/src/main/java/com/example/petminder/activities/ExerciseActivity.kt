package com.example.petminder.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.petminder.R
import com.example.petminder.databinding.ActivityExerciseBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.ExerciseModel
import com.example.petminder.models.PetModel
import timber.log.Timber

class ExerciseActivity : AppCompatActivity(){

    private lateinit var binding: ActivityExerciseBinding
    var pet = PetModel()
    var exercise = ExerciseModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var edit = false

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        Timber.i("Feed Activity started....")

        if(intent.hasExtra("exercise_edit")){
            Timber.i("Editing exercise")
            edit = true
            exercise =  intent.extras?.getParcelable("exercise_edit")!!
            binding.exerciseType.setText(exercise.name)
            binding.exerciseLength.setText(exercise.length.toString())
        }

        binding.btnAdd.setOnClickListener(){
            pet  = intent.extras?.getParcelable("pet")!!
            exercise.petId = pet.id
            exercise.name = binding.exerciseType.text.toString()
            exercise.length = binding.exerciseLength.text.toString().toInt()
//            if (pet.name.isEmpty()) {
//                Snackbar.make(it, R.string.enter_pet_title, Snackbar.LENGTH_SHORT).show()
//            } else{
            if (edit) {
                app.exercises.update(exercise.copy())
            } else {
                app.exercises.create(exercise.copy())
            }
//            }
            setResult(RESULT_OK)
            finish()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_delete, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.exercises.deleteOne(exercise.id)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}