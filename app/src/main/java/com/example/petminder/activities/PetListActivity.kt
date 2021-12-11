package com.example.petminder.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = PetAdapter(app.pets.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.item_add -> {
                val launcherIntent = Intent(this, PetActivity::class.java)
                startActivityForResult(launcherIntent, 0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override  fun onPetClick(pet: PetModel){
        val launcherIntent = Intent(this, PetActivity::class.java)
        launcherIntent.putExtra("pet_edit", pet)
        startActivityForResult(launcherIntent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}
