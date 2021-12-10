package com.example.petminder.models

import timber.log.Timber.i

class PetMemStore : PetStore {
    val pets = ArrayList<PetModel>()

    override fun findAll(): List<PetModel>{
        return pets
    }

    override fun create(pet: PetModel) {
        pets.add(pet)
    }

    fun logAll(){
        pets.forEach{ i("${it}") }
    }
}