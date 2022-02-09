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
            foundPet.weight = pet.weight
            foundPet.age = pet.age
            foundPet.image = pet.image
            logAll()
        }
    }

    override fun findOne(id: Long) : PetModel? {
        var foundPet: PetModel? = pets.find { p -> p.id == id }
        return foundPet
    }

    override fun deleteOne(id: Long) {
        val foundExercise = findOne(id)
        if(foundExercise!= null){
            pets.remove(foundExercise)
        }

    }


    fun logAll(){
        pets.forEach{ i("${it}") }
    }
}