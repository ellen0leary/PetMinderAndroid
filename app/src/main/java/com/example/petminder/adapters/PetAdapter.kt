package com.example.petminder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petminder.databinding.CardPetBinding
import com.example.petminder.models.PetModel

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