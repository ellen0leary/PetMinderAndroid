package com.example.petminder.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.petminder.R
import com.example.petminder.databinding.ActivityFeedBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.FeedModel
import com.example.petminder.models.PetModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i

class FeedActivity : AppCompatActivity(){

    private lateinit var binding: ActivityFeedBinding
    var pet = PetModel()
    var feed = FeedModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var edit = false

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        i("Feed Activity started....")

        ArrayAdapter.createFromResource(
            this,
            R.array.timeOfDay,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter
        }


        if(intent.hasExtra("feed_edit")){
            i("Editing feed")
            edit = true
            feed =  intent.extras?.getParcelable("feed_edit")!!
            binding.spinner.setSelection(
                (binding.spinner.getAdapter() as ArrayAdapter<String?>).getPosition(
                    feed.time
                )
            )

            binding.spinner.selectedItem.toString()
            binding.feedWeight.setText(feed.weigth.toString())
            binding.btnAdd.setText(R.string.update_feed_btn)
        }

        binding.btnAdd.setOnClickListener(){
            pet  = intent.extras?.getParcelable("pet")!!
            feed.petId = pet.id
            feed.time = binding.spinner.selectedItem.toString()
            feed.weigth = binding.feedWeight.text.toString().toFloat()
            if (feed.time.isEmpty()) {
                Snackbar.make(it, R.string.enter_pet_title, Snackbar.LENGTH_SHORT).show()
            } else{
                if (edit) {
                    app.feeds.update(feed.copy())
                } else {
                    app.feeds.create(feed.copy())
                }
            }
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
                app.feeds.deleteOne(feed.id)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}