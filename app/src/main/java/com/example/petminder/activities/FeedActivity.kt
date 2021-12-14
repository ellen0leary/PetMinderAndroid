package com.example.petminder.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.petminder.databinding.ActivityFeedBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.FeedModel
import com.example.petminder.models.PetModel
import timber.log.Timber
import timber.log.Timber.i

class FeedActivity : AppCompatActivity(){

    private lateinit var binding: ActivityFeedBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var pet = PetModel()
    var feed = FeedModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val edit = false

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        i("Feed Activity started....")


        binding.btnAdd.setOnClickListener(){
            pet  = intent.extras?.getParcelable("pet")!!
            feed.petId = pet.id
            feed.time = binding.feedTime.text.toString()
            feed.weigth = binding.feedWeight.text.toString().toFloat()
//            if (pet.name.isEmpty()) {
//                Snackbar.make(it, R.string.enter_pet_title, Snackbar.LENGTH_SHORT).show()
//            } else{
            if (edit) {
                app.feeds.update(feed.copy())
            } else {
                app.feeds.create(feed.copy())
            }
//            }
            setResult(RESULT_OK)
            finish()
        }

    }
}