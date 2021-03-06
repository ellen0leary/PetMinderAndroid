package com.example.petminder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petminder.databinding.CardPetBinding
import com.example.petminder.models.pets.PetModel
import com.squareup.picasso.Picasso

interface PetListener {
    fun onPetClick(pet: PetModel)
}

class PetAdapter constructor(private var pets: ArrayList<PetModel>, private val listener: PetListener,private val readOnly: Boolean) :
    RecyclerView.Adapter<PetAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding, readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val pet = pets[holder.adapterPosition]
        holder.bind(pet, listener)
    }

    fun removeAt(position: Int) {
        pets.removeAt(position)
        notifyItemRemoved(position)
    }
    override fun getItemCount(): Int = pets.size

    class MainHolder(private val binding : CardPetBinding, private val readOnly : Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly

        fun bind(pet: PetModel, listener: PetListener) {
//            binding.PetName.text = pet.name
//            Picasso.get()
//                .load(pet.image)
//                .centerCrop()
//                .resize(360,360) //resize image
//                .into(binding.imageView)
            binding.pet = pet
            binding.root.tag = pet
            binding.root.setOnClickListener{listener.onPetClick(pet)}
            binding.executePendingBindings()
        }
    }
}

