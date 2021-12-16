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
import com.example.petminder.adapters.ExerciseAdapter
import com.example.petminder.adapters.ExercsieListener
import com.example.petminder.adapters.FeedAdapter
import com.example.petminder.adapters.FeedListener
import com.example.petminder.databinding.ActivityInfoPetBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.ExerciseModel
import com.example.petminder.models.FeedModel
import com.example.petminder.models.PetModel
import com.squareup.picasso.Picasso
import timber.log.Timber.i


class PetInfoActivity : AppCompatActivity(), FeedListener, ExercsieListener{

    lateinit var app: MainApp
    private lateinit var binding: ActivityInfoPetBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    var pet = PetModel()
    var tab = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoPetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tab="feed"

        //the pet
        pet  = intent.extras?.getParcelable("pet_info")!!

        binding.toolbarAdd.title = pet.name
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recycler.layoutManager = layoutManager
        loadFeeds()

//        val exerLayoutManager = LinearLayoutManager(this)
//        binding.exerciseRecycler.layoutManager = exerLayoutManager

//        loadExercises()

//        binding.recycler
//        binding.tabs.
//        binding.tabLayout.setOnTabChangedListener(OnTabChangeListene
        registerRefreshCallback()
    }

    public fun addNewFeed(view: View) {
        i("adding new Feed")
        val launcherIntent = Intent(this, FeedActivity::class.java)
        launcherIntent.putExtra("pet", pet)
        startActivity(launcherIntent)
    }

    public fun addNewExercise(view: View) {
        val launcherIntent = Intent(this, ExerciseActivity::class.java)
        launcherIntent.putExtra("pet", pet)
        startActivity(launcherIntent)
    }

    public fun setFeedTab(view: View){
        i("loading Feeds")
        loadFeeds()
    }

    public fun setExerciseTab(view: View){
        i("loading Exercises")
        loadExercises()
    }

    public fun setTab(view: View){
        if(tab=="feed") {
            i("loading Exercises")
            loadExercises()
            tab="exer"
        }else if(tab=="exer"){
            i("loading Feeds")
            loadFeeds()
            tab="feed"
        }
    }

    override fun onResume() {
        pet = app.pets.findOne(pet.id)!!
        i(pet.toString())
        Picasso.get().load(pet.image).into(binding.petImage)
        val ageText = "Age - " + pet.age.toString() + " years"
        binding.ageText.setText(ageText)

        val weightText = "Weight- " + pet.weight.toString() + "Kg"
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
        binding.recycler.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun loadFeeds(){
        showFeeds(app.feeds.findByPet(pet.id))
    }

    fun showFeeds(feeds: List<FeedModel>){
        i(feeds.size.toString())
        binding.recycler.adapter = FeedAdapter(feeds,this)
        binding.recycler.adapter?.notifyDataSetChanged()
    }

    private fun loadExercises(){
        showExercises(app.exercises.findByPet(pet.id))
    }

    fun showExercises(exercise: List<ExerciseModel>){
        i(exercise.size.toString())
        binding.recycler.adapter = ExerciseAdapter(exercise,this)
        binding.recycler.adapter?.notifyDataSetChanged()
    }

    override fun onFeedClick(feed: FeedModel) {
        i("Click on feeds")
        val launcherIntent = Intent(this, FeedActivity::class.java)
        launcherIntent.putExtra("pet", pet)
        launcherIntent.putExtra("feed_edit", feed)
        refreshIntentLauncher.launch(launcherIntent)
    }

    override fun onExerciseClick(exercise: ExerciseModel) {
        i("Click on exercise")
    }




}

