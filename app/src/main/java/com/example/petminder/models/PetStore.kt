package com.example.petminder.models

interface PetStore {
    fun findAll(): List<PetModel>
    fun create(pet: PetModel)
}