package com.example.petminder.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petminder.R
import com.example.petminder.adapters.FeedAdapter
import com.example.petminder.adapters.FeedListener
import com.example.petminder.databinding.ActivityInfoPetBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.FeedModel
import com.example.petminder.models.PetModel
import com.squareup.picasso.Picasso
import timber.log.Timber.i

class PetInfoActivity : AppCompatActivity(), FeedListener{

    lateinit var app: MainApp
    private lateinit var binding: ActivityInfoPetBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
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

        val layoutManager = LinearLayoutManager(this)
        binding.feedRecycler.layoutManager = layoutManager
        loadFeeds()
        registerRefreshCallback()
    }

    public fun addNewFeed(view: View) {
        i("adding new Feed")

        openFeed()
    }

    fun openFeed() {
        val launcherIntent = Intent(this, FeedActivity::class.java)
        launcherIntent.putExtra("pet", pet)
        startActivity(launcherIntent)

    }

    override fun onResume() {
        pet = app.pets.findOne(pet.id)!!
        i(pet.toString())
        Picasso.get().load(pet.image).into(binding.petImage)
        val ageText = "Age - " + pet.age.toString()
        binding.ageText.setText(ageText)

        val weightText = "Weight - " + pet.weight.toString() + " Kg"
        binding.weightText.setText(weightText)

        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.item_add -> {
                val launcherIntent = Intent(this, PetActivity::class.java)
                launcherIntent.putExtra("pet_edit", pet)
                startActivityForResult(launcherIntent, 0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerRefreshCallback(){
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                loadFeeds()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        binding.feedRecycler.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun loadFeeds(){
        showFeeds(app.feeds.findByPet(pet.id))
    }

    fun showFeeds(feeds: List<FeedModel>){
        i(feeds.size.toString())
        binding.feedRecycler.adapter = FeedAdapter(feeds,this)
        binding.feedRecycler.adapter?.notifyDataSetChanged()
    }

    override fun onFeedClick(feed: FeedModel) {
        i("Click on feeds")
        TODO("Not yet implemented")
    }
}