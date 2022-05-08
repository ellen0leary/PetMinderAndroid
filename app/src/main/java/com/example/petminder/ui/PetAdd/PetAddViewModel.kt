package com.example.petminder.ui.PetAdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petminder.firebase.db.PetDBManager
import com.example.petminder.models.pets.PetManager
import com.example.petminder.models.pets.PetModel
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber
import java.lang.IllegalArgumentException

class PetAddViewModel: ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatis: LiveData<Boolean>
        get() = status


    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    fun addPet(firebaseUser: MutableLiveData<FirebaseUser>, pet: PetModel){
        status.value = try{
//            PetManager.create(pet)
            PetDBManager.create(firebaseUser, pet)
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }

    fun updatePet(firebaseUser: MutableLiveData<FirebaseUser>, pet: PetModel){
        status.value = try{
            Timber.i(pet.toString())
            PetDBManager.update(firebaseUser.value?.uid!!,pet.uid!!, pet)
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }
}