package com.example.petminder.models.pets

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface PetStore {
    fun findAll(petList: MutableLiveData<List<PetModel>>)
    fun findAll(userid:String, petList: MutableLiveData<List<PetModel>>)
    fun findOne(userid: String, petid: String, pet: MutableLiveData<PetModel>)

    fun create(firbaseUser: MutableLiveData<FirebaseUser>, pet: PetModel)
    fun update(userid:String, petid: String, pet: PetModel)
    fun deleteOne(userid: String, petid: String, pet: PetModel)
}