package com.example.petminder.ui.PetAdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petminder.models.pets.PetManager
import com.example.petminder.models.pets.PetModel
import java.lang.IllegalArgumentException

class PetAddViewModel: ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatis: LiveData<Boolean>
        get() = status

    fun addPet(pet: PetModel){
        status.value = try{
            PetManager.create(pet)
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }
}