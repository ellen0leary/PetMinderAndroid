package com.example.petminder.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petminder.R
import com.example.petminder.adapters.PetAdapter
import com.example.petminder.adapters.PetListener
import com.example.petminder.databinding.ActivityPetListBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.PetModel

class PetListActivity :  PetListener,AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityPetListBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = GridLayoutManager(this,3)
        binding.recyclerView.layoutManager = layoutManager
        loadPets()
        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, PetActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPetClick(pet: PetModel) {
        val launcherIntent = Intent(this, PetInfoActivity::class.java)
        launcherIntent.putExtra("pet_info", pet)
        refreshIntentLauncher.launch(launcherIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun registerRefreshCallback(){
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                loadPets()
            }
    }

    private fun loadPets(){
        showPets(app.pets.findAll())
    }

    fun showPets(pets: List<PetModel>){
        binding.recyclerView.adapter = PetAdapter(pets,this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}