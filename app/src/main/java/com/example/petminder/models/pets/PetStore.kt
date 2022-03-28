package com.example.petminder.models.pets

interface PetStore {
    fun findAll(): List<PetModel>
    fun create(pet: PetModel)
    fun update(pet: PetModel)
    fun findOne(id: Long) : PetModel?
    fun deleteOne(id: Long)
}