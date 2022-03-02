package com.example.petminder.ui.PetList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petminder.models.PetManager
import com.example.petminder.models.PetModel

class PetListViewModel: ViewModel() {
    private val petList = MutableLiveData<List<PetModel>>()

    val observablePetList: LiveData<List<PetModel>>
        get() = petList

    init {
        load()
    }

    fun load() {
        petList.value = PetManager.findAll()
    }
}