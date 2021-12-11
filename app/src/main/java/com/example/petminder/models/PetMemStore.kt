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

    override fun update(pet: PetModel) {
        var foundPet: PetModel? = pets.find {p-> p.id == pet.id}
        if(foundPet!= null){
            foundPet.name = pet.name
            logAll()
        }
    }

    fun logAll(){
        pets.forEach{ i("${it}") }
    }
}