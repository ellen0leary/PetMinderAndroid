package com.example.petminder.activities

import android.os.Bundle
import android.view.LayoutInflater
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
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = PetAdapter(app.pets)
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