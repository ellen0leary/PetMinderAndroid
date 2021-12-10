package com.example.petminder.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petminder.R
import com.example.petminder.databinding.ActivityPetListBinding
import com.example.petminder.databinding.CardPetBinding
import com.example.petminder.main.MainApp
import com.example.petminder.models.PetModel

class PetListActivity : AppCompatActivity() {

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
        binding.recyclerView.adapter = PetAdapter(app.pets)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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
}

class PetAdapter constructor(private var pets: List<PetModel>) :
    RecyclerView.Adapter<PetAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val pets = pets[holder.adapterPosition]
        holder.bind(pets)
    }

    override fun getItemCount(): Int = pets.size

    class MainHolder(private val binding : CardPetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pet: PetModel) {
            binding.PetName.text = pet.name
        }
    }
}