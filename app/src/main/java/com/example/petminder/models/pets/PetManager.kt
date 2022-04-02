package com.example.petminder.models.pets

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber.i

object PetManager : PetStore {
    val pets = ArrayList<PetModel>()

     fun findAll(): List<PetModel>{
        return pets
    }

     fun create(pet: PetModel) {
        pets.add(pet)
    }

     fun update(pet: PetModel) {
//        var foundPet: PetModel? = pets.find { p-> p.id == pet.id}
//        if(foundPet!= null){
//            foundPet.name = pet.name
//            foundPet.weight = pet.weight
//            foundPet.age = pet.age
//            foundPet.type = pet.type
//            foundPet.image = pet.image
//            logAll()
//        }
    }

     fun findOne(id: Long) : PetModel? {
//        var foundPet: PetModel? = pets.find { p -> p.id == id }
//        return foundPet
         return PetModel()
    }

     fun deleteOne(id: Long) {
        val foundExercise = findOne(id)
        if(foundExercise!= null){
            pets.remove(foundExercise)
        }

    }


    fun logAll(){
        pets.forEach{ i("${it}") }
    }

    override fun findAll(petList: MutableLiveData<List<PetModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, petList: MutableLiveData<List<PetModel>>) {
        TODO("Not yet implemented")
    }

    override fun findOne(userid: String, petid: String, pet: MutableLiveData<PetModel>) {
        TODO("Not yet implemented")
    }

    override fun create(firbaseUser: MutableLiveData<FirebaseUser>, pet: PetModel) {
        TODO("Not yet implemented")
    }

    override fun update(userid: String, petid: String, pet: PetModel) {
        TODO("Not yet implemented")
    }


    override fun deleteOne(userid: String, petId: String, pet: PetModel) {
        TODO("Not yet implemented")
    }
}