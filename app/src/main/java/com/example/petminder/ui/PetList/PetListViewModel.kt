package com.example.petminder.ui.PetList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petminder.firebase.db.PetDBManager
import com.example.petminder.models.pets.PetManager
import com.example.petminder.models.pets.PetModel
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber
import java.lang.Exception

class PetListViewModel: ViewModel() {
    private val petList = MutableLiveData<List<PetModel>>()

    val observablePetList: LiveData<List<PetModel>>
        get() = petList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()
    init {
        load()
    }

    fun load() {
        try {
            PetDBManager.findAll(liveFirebaseUser.value?.uid!!, petList)
            Timber.i("Report Load Success : ${petList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }


    fun delete(userid: String, id: String) {
        try {
            PetDBManager.deleteOne(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}